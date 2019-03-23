package com.djavid.checksonline.features.shops

import com.djavid.checksonline.dagger.qualifiers.Title
import com.djavid.checksonline.features.app.Screens
import com.djavid.checksonline.interactors.ChecksInteractor
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.model.entities.PlaceholderDate
import com.djavid.checksonline.model.entities.Receipt
import io.reactivex.disposables.Disposable
import org.joda.time.DateTime
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class ShopsPresenter @Inject constructor(
        private val view: ShopsContract.View,
        private val checksInteractor: ChecksInteractor,
        private val interval: DateInterval,
        @Title private val title: String,
        private val router: Router
) : ShopsContract.Presenter {

    private var currentPage: Int = 0
    private var hasNext: Boolean = false
    private var lastDate: DateTime? = null
    private var disposable: Disposable? = null


    override fun init() {
        view.init(this)
        view.setToolbarTitle(title)
        refresh()
    }

    override fun onDestroy() {
        disposable?.dispose()
    }

    override fun onCheckClicked(receipt: Receipt) {
        router.navigateTo(Screens.CHECK_ACTIVITY, receipt.receiptId.toString())
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