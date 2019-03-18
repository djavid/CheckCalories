package com.djavid.checksonline.features.stats

import com.djavid.checksonline.features.base.BaseView
import com.djavid.checksonline.model.entities.DateInterval

interface StatsView : BaseView {

    fun setViewPager(intervals: List<DateInterval>)
    fun setToolbarSum(totalSum: Double)

}