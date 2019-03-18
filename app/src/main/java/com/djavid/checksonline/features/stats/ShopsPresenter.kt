package com.djavid.checksonline.features.stats

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.Screens
import com.djavid.checksonline.dagger.qualifiers.Title
import com.djavid.checksonline.features.base.BasePresenter
import com.djavid.checksonline.interactors.ChecksInteractor
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.model.entities.PlaceholderDate
import com.djavid.checksonline.model.entities.Receipt
import org.joda.time.DateTime
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ShopsPresenter @Inject constructor(
        private val checksInteractor: ChecksInteractor,
        private val interval: DateInterval,
        @Title private val title: String,
        router: Router
) : BasePresenter<ShopsView>(router)  {

    private var currentPage: Int = 0
    private var hasNext: Boolean = false
    private var lastDate: DateTime? = null


    override fun onFirstViewAttach() {
        viewState.setToolbarTitle(title)
        refresh()
    }

    fun onCheckClicked(receipt: Receipt) {
        router.navigateTo(Screens.CHECK_ACTIVITY, receipt.receiptId.toString())
    }

    fun loadMoreChecks() {
        if (hasNext) loadChecks(currentPage + 1, false)
        else viewState.noMoreToLoad()
    }

    fun refresh() {
        loadChecks(0, true)
    }

    private fun loadChecks(page: Int, show: Boolean) {
        currentPage = page

        try {
            val dateStart = DateTime.parse(interval.dateStart).millis
            val dateEnd = DateTime.parse(interval.dateEnd).millis

            checksInteractor.getChecksByShop(title, dateStart, dateEnd, page)
                    .doOnSubscribe { if (show) viewState.showProgress(true) }
                    .doAfterTerminate { if (show) viewState.showProgress(false) }
                    .subscribe({
                        hasNext = it.result?.hasNext ?: false
                        viewState.showChecks(it.result?.receipts ?: listOf(), page == 0)
                    }, {
                        processError(it)
                    })

        } catch (it: Throwable) {
            processError(it)
        }
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