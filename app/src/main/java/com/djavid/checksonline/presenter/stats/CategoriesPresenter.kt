package com.djavid.checksonline.presenter.stats

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.base.BasePresenter
import com.djavid.checksonline.interactors.ChecksInteractor
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.model.entities.PlaceholderDate
import com.djavid.checksonline.model.entities.StatItem
import com.djavid.checksonline.toothpick.qualifiers.Title
import org.joda.time.DateTime
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class CategoriesPresenter @Inject constructor(
        private val interactor: ChecksInteractor,
        private val interval: DateInterval,
        @Title private val title: String,
        router: Router
) : BasePresenter<CategoriesView>(router) {

    private var currentPage: Int = 0
    private var hasNext: Boolean = false
    private var lastDate: DateTime? = null


    override fun onFirstViewAttach() {
        viewState.setToolbarTitle(title)
        refresh()
    }

    fun loadMoreChecks() {
        if (hasNext) loadItems(currentPage + 1, false)
        else viewState.noMoreToLoad()
    }

    fun refresh() {
        loadItems(0, true)
    }

    private fun loadItems(page: Int, show: Boolean) {
        currentPage = page

        try {
            val dateStart = DateTime.parse(interval.dateStart).millis
            val dateEnd = DateTime.parse(interval.dateEnd).millis

            interactor.getItemsByCategory(title, dateStart, dateEnd, page)
                    .doOnSubscribe { if (show) viewState.showProgress(true) }
                    .doAfterTerminate { if (show) viewState.showProgress(false) }
                    .subscribe({
                        hasNext = it.result?.hasNext ?: false
                        viewState.showItems(it.result?.items ?: listOf(), page == 0)
                    }, {
                        processError(it)
                    })

        } catch (it: Throwable) {
            processError(it)
        }
    }

    fun getPlaceholderDates(checks: List<StatItem>) : List<PlaceholderDate> {
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
}