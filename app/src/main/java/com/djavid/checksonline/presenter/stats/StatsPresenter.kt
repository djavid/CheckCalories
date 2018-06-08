package com.djavid.checksonline.presenter.stats

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.Screens
import com.djavid.checksonline.base.BasePresenter
import com.djavid.checksonline.base.Dates
import com.djavid.checksonline.interactors.StatsInteractor
import com.djavid.checksonline.model.entities.DateInterval
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
                .doOnSubscribe({
                    viewState.showProgress(true)
                    unsubscribeOnDestroy(it)
                })
                .doAfterTerminate { viewState.showProgress(false) }
                .subscribe({
                    intervals = it.result
                    viewState.setViewPager(it.result.asReversed())
                }, { processError(it) })
    }

    fun onViewPagerScrolled(position: Int) {
        //if (position >= 0 && position < intervals.size)
            //getTotalSum(intervals[position])
    }

    private fun getTotalSum(interval: Dates) {
        statsInteractor.getTotalSum(interval.name)
                .subscribe({
                    viewState.setToolbarSum(it.result)
                }, {
                    processError(it)
                })
    }

    fun onHabitsClicked() {
        router.navigateTo(Screens.HABITS_ACTIVITY)
    }

}