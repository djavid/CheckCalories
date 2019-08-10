package com.djavid.checksonline.view.stats_item

import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.model.entities.Percentage
import com.github.mikephil.charting.data.PieEntry

class StatsItemContract {

    interface View {
        fun init(presenter: Presenter, interval: DateInterval)
        fun showProgress(show: Boolean)
        fun setPercentagesData(list: List<Percentage>)
        fun setChartData(list: List<Percentage>)
        fun setSwitchBtn(checked: Boolean)
    }

    interface Presenter {
        fun init(interval: DateInterval)
        fun onDestroy()
        fun onResume()
        fun onSwitchClicked(checked: Boolean)
        fun onPercentageClicked(percentage: Percentage)
        fun onChartValueSelected(entry: PieEntry)
        fun onNothingSelected()
    }
}