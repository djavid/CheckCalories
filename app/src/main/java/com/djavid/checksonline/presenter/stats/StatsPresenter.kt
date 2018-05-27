package com.djavid.checksonline.presenter.stats

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.base.BasePresenter
import com.djavid.checksonline.base.Dates
import com.djavid.checksonline.interactors.StatsInteractor
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.model.networking.responses.StatPercentResponse
import org.joda.time.DateTime
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class StatsPresenter @Inject constructor(
        private val statsInteractor: StatsInteractor,
        router: Router
) : BasePresenter<StatsView>(router) {

    private var statResponse: StatPercentResponse? = null
    private var currentInterval: Dates = Dates.MONTH
    private var isShop: Boolean = false


    override fun onFirstViewAttach() {
        //getStats(1495551600000, 1527087600000, false, true)

        onDateIntervalChosen(Dates.MONTH)
    }

    fun onSwitchClicked(checked: Boolean) {
        isShop = checked

        if (statResponse != null) {
            showStats(statResponse!!, checked)

            if (checked) viewState.refreshCurrentChartData(statResponse!!.result.shops)
            else viewState.refreshCurrentChartData(statResponse!!.result.categories)

        } else {
            getStats(1495551600000, 1527087600000, checked, false)
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
                    viewState.setChartIntervals(it.result)
                }, { processError(it) })
    }

    private fun getStats(start: Long, end: Long, shop: Boolean, showProgress: Boolean) {

        statsInteractor.getChecks(start, end)
                .doOnSubscribe({
                    viewState.showProgress(true)
                    unsubscribeOnDestroy(it)
                })
                .doAfterTerminate { if (showProgress) viewState.showProgress(false) }
                .subscribe(
                        {
                            statResponse = it
                            showStats(it, shop)
                        },
                        { processError(it) })
    }

    private fun showStats(it: StatPercentResponse, shop: Boolean) {
//        if (shop) viewState.setChartData(it.result.shops)
//        else viewState.setChartData(it.result.categories)

        viewState.setToolbarSum(it.result.totalSum)

        if (shop) viewState.setPercentages(it.result.shops.sortedByDescending { it.percentageSum })
        else viewState.setPercentages(it.result.categories.sortedByDescending { it.percentageSum })
    }

    fun onChartItemScrolled(pos: Int) {
        if (statResponse != null) {
            showStats(statResponse!!, isShop)
        }
    }

    fun onChartItemResolved(interval: DateInterval, pos: Int) {
        println("Resolved Item Position: $pos")

        val start = DateTime.parse(interval.dateStart).millis
        val end = DateTime.parse(interval.dateEnd).millis

        statsInteractor.getChecks(start, end)
//                .doOnSubscribe({ if (showProgress) viewState.showProgress(true) })
//                .doAfterTerminate { if (showProgress) viewState.showProgress(false) }
                .doOnSubscribe { unsubscribeOnDestroy(it) }
                .subscribe(
                        {
                            statResponse = it
                            viewState.setChartItemData(pos, it.result.categories)
                            showStats(it, isShop)
                        },
                        { processError(it) })
    }

    fun onDateIntervalChosen(interval: Dates) {
        currentInterval = interval

        when (interval) {
            Dates.MONTH -> getIntervals("month")
            Dates.WEEK -> getIntervals("week")
            Dates.DAY -> getIntervals("day")
            Dates.OWN -> println("Own")
        }
    }

}