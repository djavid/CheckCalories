package com.djavid.checksonline.view.categories

import com.djavid.checksonline.interactors.ChecksInteractor
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.model.entities.PlaceholderDate
import com.djavid.checksonline.model.entities.StatItem
import io.reactivex.disposables.Disposable
import org.joda.time.DateTime
import ru.terrakok.cicerone.Router

class CategoriesPresenter constructor(
        private val view: CategoriesContract.View,
        private val interactor: ChecksInteractor,
//        @Title private val title: String,
        private val router: Router
) : CategoriesContract.Presenter {

    private var currentPage: Int = 0
    private var hasNext: Boolean = false
    private var lastDate: DateTime? = null
    private var disposable: Disposable? = null
    private lateinit var dateInterval: DateInterval

    override fun init(interval: DateInterval) {
        view.init(this)
        dateInterval = interval

        //TODO view.setToolbarTitle(title)
        refresh()

    }

    override fun loadMoreChecks() {
        if (hasNext) loadItems(currentPage + 1, false)
        else view.noMoreToLoad()
    }

    override fun refresh() {
        loadItems(0, true)
    }

    private fun loadItems(page: Int, show: Boolean) {
        currentPage = page

        try {
            val dateStart = DateTime.parse(dateInterval.dateStart).millis
            val dateEnd = DateTime.parse(dateInterval.dateEnd).millis

            val title = "" //TODO title
            disposable = interactor.getItemsByCategory(title, dateStart, dateEnd, page)
                    .doOnSubscribe { if (show) view.showProgress(true) }
                    .doAfterTerminate { if (show) view.showProgress(false) }
                    .subscribe({
                        hasNext = it.result?.hasNext ?: false
                        view.showItems(it.result?.items ?: listOf(), page == 0)
                    }, {
                        //TODO processError(it)
                    })

        } catch (it: Throwable) {
            //TODO processError(it)
        }
    }

    override fun getPlaceholderDates(checks: List<StatItem>): List<PlaceholderDate> {
        val dates = mutableListOf<PlaceholderDate>()

        checks.forEachIndexed { index, receipt ->
            if (lastDate == null) {
                val date = DateTime.parse(checks[0].datetime).withTimeAtStartOfDay()
                lastDate = date
                dates.add(PlaceholderDate(index, date))
            } else {
                val date = DateTime.parse(receipt.datetime).withTimeAtStartOfDay()

                if (!date.isEqual(lastDate)) {
                    lastDate = date
                    dates.add(PlaceholderDate(index, date))
                }
            }
        }

        return dates
    }

    override fun onDestroy() {
        disposable?.dispose()
    }
}