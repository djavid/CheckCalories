package com.djavid.checksonline.features.stats

import com.djavid.checksonline.Screens
import com.djavid.checksonline.interactors.StatsInteractor
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.model.entities.Dates
import io.reactivex.disposables.Disposable
import org.joda.time.DateTime
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class StatsPresenter @Inject constructor(
        private val view: StatsContract.View,
        private val statsInteractor: StatsInteractor,
        private val router: Router
) : StatsContract.Presenter {

    private var currentInterval: Dates = Dates.MONTH
    private var intervals: List<DateInterval> = listOf()
    private var disposable: Disposable? = null


    override fun init() {
        onDateIntervalChosen(Dates.MONTH)
    }

    override fun onDateIntervalChosen(interval: Dates) {
        currentInterval = interval

        when (interval) {
            Dates.MONTH -> getIntervals("month")
            Dates.WEEK -> getIntervals("week")
            Dates.DAY -> getIntervals("day")
            Dates.OWN -> println("Own")
            else -> {
            }
        }
    }

    override fun onDestroy() {
        disposable?.dispose()
    }

    override fun getIntervals(interval: String) {
        disposable = statsInteractor.getIntervals(interval)
                .doOnSubscribe { view.showProgress(true) }
                .doAfterTerminate { view.showProgress(false) }
                .subscribe({

                    if (it.error.isEmpty()) {
                        intervals = it.result
                        view.setViewPager(it.result.asReversed())
                        onViewPagerScrolled(intervals.size - 1)
                    } else {
                        //todo process error
                    }

                }, {
                    //TODO processError(it)
                })
    }

    override fun onViewPagerScrolled(position: Int) {
        val index = intervals.size - position - 1
        if (index in 0 until intervals.size) setTotalSum(index)
    }

    override fun setTotalSum(position: Int) {
        val start = DateTime.parse(intervals[position].dateStart).millis
        val end = DateTime.parse(intervals[position].dateEnd).millis

        disposable?.dispose()
        disposable = statsInteractor.getChecks(start, end)
                .subscribe(
                        {
                            view.setToolbarSum(it.result.totalSum)
                        },
                        {
                            //TODO processError(it)
                        })
    }

    override fun onHabitsClicked() {
        router.navigateTo(Screens.HABITS_ACTIVITY)
    }

}