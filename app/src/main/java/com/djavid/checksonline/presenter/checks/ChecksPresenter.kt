package com.djavid.checksonline.presenter.checks

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.Screens
import com.djavid.checksonline.base.BasePresenter
import com.djavid.checksonline.base.Dates
import com.djavid.checksonline.base.Paginator
import com.djavid.checksonline.interactors.ChecksInteractor
import com.djavid.checksonline.interactors.StatsInteractor
import com.djavid.checksonline.model.entities.DataPage
import com.djavid.checksonline.model.entities.PlaceholderDate
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.utils.SavedPreferences
import org.joda.time.DateTime
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ChecksPresenter @Inject constructor(
        private val interactor: ChecksInteractor,
        private val statsInteractor: StatsInteractor,
        private val preferences: SavedPreferences,
        router: Router
) : BasePresenter<ChecksView>(router) {

    private val checksFactory = { page: Int ->
        interactor.getChecks(page)
                .map {

                    DataPage(it.result?.receipts ?: listOf(),
                            it.result?.hasNext == true)
                }
    }
    private val checksController = ChecksController(viewState)
    private val checksPaginator = Paginator(checksFactory, checksController)
    private var lastDate: DateTime? = null

    override fun onFirstViewAttach() {
        onDateIntervalChosen(Dates.valueOf(preferences.getTotalSumInterval()))
        refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        checksPaginator.release()
    }

    fun onFabClicked() {
        router.navigateTo(Screens.QR_CODE)
    }

    fun onCheckClicked(receipt: Receipt) {
        router.navigateTo(Screens.CHECK_ACTIVITY, receipt.receiptId.toString())
    }

    fun loadMoreChecks() { //TODO
        checksPaginator.loadNewPage()
    }

    fun refresh() {
        viewState.removeAllViews()
        viewState.noMoreToLoad()
        viewState.setLoadMoreResolver()
        checksPaginator.refresh()
    }

    private fun getTotalSum(interval: Dates) {
        statsInteractor.getTotalSum(interval.name)
                .subscribe({
                    viewState.setToolbarSum(it.result)
                }, {
                    processError(it)
                })
    }

    fun onDateIntervalChosen(interval: Dates) {
        preferences.setTotalSumInterval(interval.name)

        viewState.setBtnPeriodText(interval)
        getTotalSum(interval)
    }

    fun removeCheck(id: Long) {
        interactor.removeCheck(id)
                .subscribe({

                }, {
                    processError(it)
                })
    }

    fun getPlaceholderDates(checks: List<Receipt>) : List<PlaceholderDate> {
        val dates = mutableListOf<PlaceholderDate>()

        checks.forEachIndexed { index, receipt ->
            if (lastDate == null) {
                val date = DateTime.parse(checks[0].dateTime).withTimeAtStartOfDay()
                lastDate = date
                dates.add(PlaceholderDate(index, date))
            } else {
                val date = DateTime.parse(receipt.dateTime).withTimeAtStartOfDay()

                if (!date.isEqual(lastDate)) {
                    lastDate = date
                    dates.add(PlaceholderDate(index, date))
                }
            }
        }

        return dates
    }

}