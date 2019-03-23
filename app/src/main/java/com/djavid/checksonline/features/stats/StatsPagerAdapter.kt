package com.djavid.checksonline.features.stats

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.djavid.checksonline.features.stats_item.StatItemFragment
import com.djavid.checksonline.model.entities.DateInterval

class StatsPagerAdapter(
        private val context: Context,
        fm: FragmentManager,
        private val intervals: List<DateInterval>
) : FragmentPagerAdapter(fm) {


    private val items = hashMapOf<Int, Fragment>()
//            .apply {
//                intervals.forEachIndexed { index, dateInterval ->
//                    put(index, StatItemFragment.newInstance(dateInterval))
//                }
//            }

    override fun getItem(position: Int): Fragment {

        if (items.contains(position))
            return items[position] ?: throw IllegalArgumentException("Items position is out of bound")

        items[position] = StatItemFragment.newInstance(intervals[position])
        return items[position] ?: throw IllegalArgumentException("Items position is out of bound")
    }

    override fun getCount() = intervals.size

}