package com.djavid.checksonline.features.stats_item

import com.djavid.checksonline.features.app.Screens
import com.djavid.checksonline.interactors.StatsInteractor
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.model.entities.Percentage
import com.djavid.checksonline.model.entities.StatsListData
import com.djavid.checksonline.model.networking.responses.StatPercentResponse
import com.djavid.checksonline.utils.SavedPreferences
import com.github.mikephil.charting.data.PieEntry
import io.reactivex.disposables.Disposable
import org.joda.time.DateTime
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class StatsItemPresenter @Inject constructor(
        private val view: StatsItemContract.View,
        private val statsInteractor: StatsInteractor,
        private val interval: DateInterval,
        private val preferences: SavedPreferences,
        private val router: Router
) : StatsItemContract.Presenter {

    private var statResponse: StatPercentResponse? = null
    private var isShop = preferences.getIsShop()
    private var disposable: Disposable? = null


    override fun init(interval: DateInterval) {
        view.init(this, interval)
        //view.setSwitchBtn(preferences.getIsShop()) //TODO

        val start = DateTime.parse(interval.dateStart).millis
        val end = DateTime.parse(interval.dateEnd).millis

        getStats(start, end, preferences.getIsShop(), true)
    }

    override fun onDestroy() {
        disposable?.dispose()
    }

    override fun onResume() {
        view.setSwitchBtn(preferences.getIsShop())
        if (isShop != preferences.getIsShop()) {
            val start = DateTime.parse(interval.dateStart).millis
            val end = DateTime.parse(interval.dateEnd).millis

            getStats(start, end, preferences.getIsShop(), true)
        }
    }

    private fun getStats(start: Long, end: Long, shop: Boolean, showProgress: Boolean) {
        disposable = statsInteractor.getChecks(start, end)
                .doOnSubscribe { view.showProgress(true) }
                .doAfterTerminate { if (showProgress) view.showProgress(false) }
                .subscribe(
                        {
                            statResponse = it
                            showStats(it, shop)
                        },
                        {
                            //TODO processError(it)
                        })
    }

    private fun showStats(it: StatPercentResponse, shop: Boolean) {
        if (shop) {
            view.setChartData(it.result.shops)
            view.setPercentagesData(it.result.shops.sortedByDescending { it.percentageSum })
        } else {
            view.setChartData(it.result.categories)
            view.setPercentagesData(it.result.categories.sortedByDescending { it.percentageSum })
        }
    }

    override fun onSwitchClicked(checked: Boolean) {
        preferences.setIsShop(checked)
        statResponse ?: return

        showStats(statResponse!!, checked)
    }

    override fun onPercentageClicked(percentage: Percentage) {
        router.navigateTo(Screens.STATS_LIST,
                StatsListData(percentage.title, preferences.getIsShop(), interval))
    }

    override fun onChartValueSelected(entry: PieEntry) {
        statResponse ?: return

        if (preferences.getIsShop()) {
            val res = statResponse!!.result.shops.find { it.title.equals(entry.label) }
            if (res != null) {
                val list = mutableListOf(res)
                view.setPercentagesData(list)
            }
        } else {
            val res = statResponse!!.result.categories.find { it.title.equals(entry.label) }
            if (res != null) {
                val list = mutableListOf(res)
                view.setPercentagesData(list)
            }
        }
    }

    override fun onNothingSelected() {
        statResponse ?: return

        if (preferences.getIsShop()) {
            view.setChartData(statResponse!!.result.shops)
            view.setPercentagesData(statResponse!!.result.shops.sortedByDescending { it.percentageSum })
        } else {
            view.setChartData(statResponse!!.result.categories)
            view.setPercentagesData(statResponse!!.result.categories.sortedByDescending { it.percentageSum })
        }
    }

}