package com.djavid.checksonline.presenter.stats

import com.djavid.checksonline.base.BaseView
import com.djavid.checksonline.model.entities.Percentage

interface StatsItemView : BaseView {
    fun setPercentagesData(list: List<Percentage>)
    fun setChartData(list: List<Percentage>)
    fun setSwitchBtn(checked: Boolean)
}