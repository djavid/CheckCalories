package com.djavid.checksonline.contracts.checks

import com.djavid.checksonline.contracts.check.CheckContract
import com.djavid.checksonline.interactors.ChecksInteractor
import com.djavid.checksonline.interactors.StatsInteractor
import com.djavid.checksonline.model.entities.DataPage
import com.djavid.checksonline.model.entities.Dates
import com.djavid.checksonline.model.entities.PlaceholderDate
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.model.repositories.MockRepository
import com.djavid.checksonline.utils.SavedPreferences
import com.djavid.checksonline.view.checks.ChecksController
import com.djavid.checksonline.view.common.Paginator
import io.reactivex.disposables.Disposable
import org.joda.time.DateTime

class ChecksPresenter constructor(
        private val view: ChecksContract.View,
        private val interactor: ChecksInteractor,
        private val statsInteractor: StatsInteractor,
        private val preferences: SavedPreferences,
        private val mockRepository: MockRepository,
        private val checkNavigator: CheckContract.Navigator
) : ChecksContract.Presenter {

    private var checks: List<Receipt> = emptyList()

    private val checksFactory = { page: Int ->
        mockRepository.getChecks().map {
            checks = it
            DataPage(it, false)
        }
    }
    private val checksController = ChecksController(view)
    private val checksPaginator = Paginator(checksFactory, checksController)
    private var lastDate: DateTime? = null
    private var disposable: Disposable? = null


    override fun init() {
        view.init(this)
		//onDateIntervalChosen(Dates.valueOf(preferences.getTotalSumInterval()))
        refresh()
    }

    override fun onDestroy() {
        checksPaginator.release()
    }

    override fun onFabClicked() {
        //qrNavigator.goToQrScreen()
    }

    override fun onCheckClicked(receipt: Receipt) {
        // bottomSheet?.openCheck(receipt)
        checkNavigator.openCheckPanel(receipt)
    }

    override fun loadMoreChecks() {
        checksPaginator.loadNewPage()
    }

    override fun refresh() {
        view.removeAllViews()
        view.noMoreToLoad()
        view.setLoadMoreResolver()
        checksPaginator.refresh()
    }

    private fun getTotalSum(interval: Dates) {
        disposable?.dispose()
        disposable = statsInteractor.getTotalSum(interval.name)
                .subscribe({
                    view.setToolbarSum(it.result)
                }, {
                    //TODO processError(it)
                })
    }

    override fun onDateIntervalChosen(interval: Dates) {
        preferences.setTotalSumInterval(interval.name)

        view.setBtnPeriodText(interval)
        getTotalSum(interval)
    }

    override fun removeCheck(id: Long) {
        disposable?.dispose()
        disposable = interactor.removeCheck(id)
                .subscribe({

                }, {
                    //TODO processError(it)
                })
    }

    override fun getPlaceholderDates(checks: List<Receipt>): List<PlaceholderDate> {
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