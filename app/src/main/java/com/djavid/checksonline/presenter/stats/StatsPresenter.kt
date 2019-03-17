package com.djavid.checksonline.presenter.stats

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.Screens
import com.djavid.checksonline.base.BasePresenter
import com.djavid.checksonline.base.Dates
import com.djavid.checksonline.interactors.StatsInteractor
import com.djavid.checksonline.model.entities.DateInterval
import org.joda.time.DateTime
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class StatsPresenter @Inject constructor(
        private val statsInteractor: StatsInteractor,
        router: Router
) : BasePresenter<StatsView>(router) {

    private var currentInterval: Dates = Dates.MONTH
    private var intervals: List<DateInterval> = listOf()


    override fun onFirstViewAttach() {
        onDateIntervalChosen(Dates.MONTH)
    }

    fun onDateIntervalChosen(interval: Dates) {
        currentInterval = interval

        when (interval) {
            Dates.MONTH -> getIntervals("month")
            Dates.WEEK -> getIntervals("week")
            Dates.DAY -> getIntervals("day")
            Dates.OWN -> println("Own")
            else -> {}
        }
    }

    private fun getIntervals(interval: String) {
        statsInteractor.getIntervals(interval)
                .doOnSubscribe {
                    viewState.showProgress(true)
                    unsubscribeOnDestroy(it)
                }
                .doAfterTerminate { viewState.showProgress(false) }
                .subscribe({

                    if (it.error.isEmpty()) {
                        intervals = it.result
                        viewState.setViewPager(it.result.asReversed())
                        onViewPagerScrolled(intervals.size - 1)
                    } else {
                        //todo process error
                    }

                }, { processError(it) })
    }

    fun onViewPagerScrolled(position: Int) {
        val index = intervals.size - position - 1
        if (index in 0 until intervals.size) setTotalSum(index)
    }

    private fun setTotalSum(position: Int) {
        val start = DateTime.parse(intervals[position].dateStart).millis
        val end = DateTime.parse(intervals[position].dateEnd).millis

        statsInteractor.getChecks(start, end)
                .doOnSubscribe({ unsubscribeOnDestroy(it) })
                .subscribe(
                        { viewState.setToolbarSum(it.result.totalSum) },
                        { processError(it) })
    }

    fun onHabitsClicked() {
        router.navigateTo(Screens.HABITS_ACTIVITY)
    }

}