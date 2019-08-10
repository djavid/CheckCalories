package com.djavid.checksonline.view.shops

import com.djavid.checksonline.interactors.ChecksInteractor
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.model.entities.PlaceholderDate
import com.djavid.checksonline.model.entities.Receipt
import io.reactivex.disposables.Disposable
import org.joda.time.DateTime
import ru.terrakok.cicerone.Router

class ShopsPresenter constructor(
        private val view: ShopsContract.View,
        private val checksInteractor: ChecksInteractor,
        //TODO @Title private val title: String,
        private val router: Router
) : ShopsContract.Presenter {

    private var currentPage: Int = 0
    private var hasNext: Boolean = false
    private var lastDate: DateTime? = null
    private var disposable: Disposable? = null
    private lateinit var interval: DateInterval


    override fun init(interval: DateInterval) {
        view.init(this)
        this.interval = interval
        //TODO view.setToolbarTitle(title)
        refresh()
    }

    override fun onDestroy() {
        disposable?.dispose()
    }

    override fun onCheckClicked(receipt: Receipt) {
//        router.navigateTo(Screens.CHECK_ACTIVITY, receipt.receiptId.toString())
    }

    override fun loadMoreChecks() {
        if (hasNext) loadChecks(currentPage + 1, false)
        else view.noMoreToLoad()
    }

    override fun refresh() {
        loadChecks(0, true)
    }

    override fun loadChecks(page: Int, show: Boolean) {
        currentPage = page

        try {
            val dateStart = DateTime.parse(interval.dateStart).millis
            val dateEnd = DateTime.parse(interval.dateEnd).millis

            val title = "" //TODO
            disposable = checksInteractor.getChecksByShop(title, dateStart, dateEnd, page)
                    .doOnSubscribe { if (show) view.showProgress(true) }
                    .doAfterTerminate { if (show) view.showProgress(false) }
                    .subscribe({
                        hasNext = it.result?.hasNext ?: false
                        view.showChecks(it.result?.receipts ?: listOf(), page == 0)
                    }, {
                        //TODO processError(it)
                    })

        } catch (it: Throwable) {
            //TODO processError(it)
        }
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