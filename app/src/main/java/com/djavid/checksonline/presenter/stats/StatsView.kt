package com.djavid.checksonline.presenter.stats

import com.djavid.checksonline.base.BaseView
import com.djavid.checksonline.model.entities.Percentage

interface StatsView : BaseView {

    fun setChartData(list: List<Percentage>)
    fun refreshCurrentChartData(list: List<Percentage>)
    fun setToolbarSum(totalSum: Double)
    fun setPercentages(list: List<Percentage>)
}