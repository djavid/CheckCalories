package com.djavid.checksonline.presenter.stats

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.Screens
import com.djavid.checksonline.base.BasePresenter
import com.djavid.checksonline.interactors.StatsInteractor
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.model.entities.Percentage
import com.djavid.checksonline.model.entities.StatsListData
import com.djavid.checksonline.model.networking.responses.StatPercentResponse
import com.djavid.checksonline.utils.SavedPreferences
import com.github.mikephil.charting.data.PieEntry
import org.joda.time.DateTime
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class StatsItemPresenter @Inject constructor(
        private val statsInteractor: StatsInteractor,
        private val interval: DateInterval,
        private val preferences: SavedPreferences,
        router: Router
) : BasePresenter<StatsItemView>(router) {

    private var statResponse: StatPercentResponse? = null


    override fun onFirstViewAttach() {

        //viewState.setSwitchBtn(preferences.getIsShop()) //TODO

        val start = DateTime.parse(interval.dateStart).millis
        val end = DateTime.parse(interval.dateEnd).millis

        getStats(start, end, preferences.getIsShop(), true)
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
        viewState.setToolbarSum(it.result.totalSum)

        if (shop) {
            viewState.setChartData(it.result.shops)
            viewState.setPercentagesData(it.result.shops.sortedByDescending { it.percentageSum })
        }
        else {
            viewState.setChartData(it.result.categories)
            viewState.setPercentagesData(it.result.categories.sortedByDescending { it.percentageSum })
        }
    }

    fun onSwitchClicked(checked: Boolean) {
        preferences.setIsShop(checked)
        statResponse ?: return

        showStats(statResponse!!, checked)
    }

    fun onPercentageClicked(percentage: Percentage) {
        router.navigateTo(Screens.STATS_LIST,
                StatsListData(percentage.title, preferences.getIsShop(), interval))
    }

    fun onChartValueSelected(entry: PieEntry) {
        statResponse ?: return

        if (preferences.getIsShop()) {
            val res = statResponse!!.result.shops.find { it.title.equals(entry.label) }
            if (res != null) {
                val list = mutableListOf(res)
                viewState.setPercentagesData(list)
            }
        } else {
            val res = statResponse!!.result.categories.find { it.title.equals(entry.label) }
            if (res != null) {
                val list = mutableListOf(res)
                viewState.setPercentagesData(list)
            }
        }
    }

    fun onNothingSelected() {
        statResponse ?: return

        if (preferences.getIsShop()) {
            viewState.setChartData(statResponse!!.result.shops)
            viewState.setPercentagesData(statResponse!!.result.shops.sortedByDescending { it.percentageSum })
        }
        else {
            viewState.setChartData(statResponse!!.result.categories)
            viewState.setPercentagesData(statResponse!!.result.categories.sortedByDescending { it.percentageSum })
        }
    }

}