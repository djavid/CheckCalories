package com.djavid.checksonline.presenter.stats

import com.djavid.checksonline.base.BaseView
import com.djavid.checksonline.model.entities.DateInterval

interface StatsView : BaseView {

    fun setViewPager(intervals: List<DateInterval>)
    fun setToolbarSum(totalSum: Double)

}