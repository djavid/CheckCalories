package com.djavid.checksonline.features.categories

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.dagger.qualifiers.Title
import com.djavid.checksonline.interactors.ChecksInteractor
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.model.entities.PlaceholderDate
import com.djavid.checksonline.model.entities.StatItem
import io.reactivex.disposables.Disposable
import org.joda.time.DateTime
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class CategoriesPresenter @Inject constructor(
        private val view: CategoriesContract.View,
        private val interactor: ChecksInteractor,
        private val interval: DateInterval,
        @Title private val title: String,
        private val router: Router
) : CategoriesContract.Presenter {

    private var currentPage: Int = 0
    private var hasNext: Boolean = false
    private var lastDate: DateTime? = null
    private var disposable: Disposable? = null


    override fun init() {
        view.init(this)

        view.setToolbarTitle(title)
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
            val dateStart = DateTime.parse(interval.dateStart).millis
            val dateEnd = DateTime.parse(interval.dateEnd).millis

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