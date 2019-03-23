package com.djavid.checksonline.features.stats

import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.model.entities.Dates

interface StatsContract {

    interface View {
        fun init(presenter: Presenter)
        fun setViewPager(intervals: List<DateInterval>)
        fun setToolbarSum(totalSum: Double)
        fun showProgress(show: Boolean)
    }

    interface Presenter {
        fun init()
        fun onDateIntervalChosen(interval: Dates)
        fun onViewPagerScrolled(position: Int)
        fun getIntervals(interval: String)
        fun setTotalSum(position: Int)
        fun onHabitsClicked()
        fun onDestroy()
    }
}