package com.djavid.checksonline.features.stats_item

import com.djavid.checksonline.features.base.BaseView
import com.djavid.checksonline.model.entities.Percentage

interface StatsItemView : BaseView {
    fun setPercentagesData(list: List<Percentage>)
    fun setChartData(list: List<Percentage>)
    fun setSwitchBtn(checked: Boolean)
}