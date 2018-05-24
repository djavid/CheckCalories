package com.djavid.checksonline.presenter.stats

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.base.BasePresenter
import com.djavid.checksonline.interactors.StatsInteractor
import com.djavid.checksonline.model.networking.responses.StatPercentResponse
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class StatsPresenter @Inject constructor(
        private val statsInteractor: StatsInteractor,
        router: Router
) : BasePresenter<StatsView>(router) {

    private var statResponse: StatPercentResponse? = null

    override fun onFirstViewAttach() {
        getStats(1495551600000, 1527087600000, false, true)
    }

    fun onSwitchClicked(checked: Boolean) {
        if (statResponse != null) {
            showStats(statResponse!!, checked)

            if (checked) viewState.refreshCurrentChartData(statResponse!!.result.shops)
            else viewState.refreshCurrentChartData(statResponse!!.result.categories)

        } else {
            getStats(1495551600000, 1527087600000, checked, false)
        }

    }

    private fun getStats(start: Long, end: Long, shop: Boolean, showProgress: Boolean) {

        statsInteractor.getChecks(start, end)
                .doOnSubscribe({ if (showProgress) viewState.showProgress(true) })
                .doAfterTerminate { if (showProgress) viewState.showProgress(false) }
                .subscribe(
                        {
                            statResponse = it
                            showStats(it, shop)
                        },
                        { processError(it) })
    }

    private fun showStats(it: StatPercentResponse, shop: Boolean) {
        if (shop) viewState.setChartData(it.result.shops)
        else viewState.setChartData(it.result.categories)

        viewState.setToolbarSum(it.result.totalSum)

        if (shop) viewState.setPercentages(it.result.shops.sortedByDescending { it.percentageSum })
        else viewState.setPercentages(it.result.categories.sortedByDescending { it.percentageSum })
    }

    fun onChartItemScrolled(pos: Int) {
        println("Snapped Item Position: $pos")
    }

}