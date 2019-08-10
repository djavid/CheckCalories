package com.djavid.checksonline.view.root

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.djavid.checksonline.view.checks.ChecksFragment
import com.djavid.checksonline.view.qr.QrFragment
import com.djavid.checksonline.view.stats.StatsFragment

class PlatesAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        val PAGE_QR = 0
        val PAGE_CHECKS = 1
        val PAGE_STATS = 2
    }

    private val fragments = listOf(QrFragment(), ChecksFragment(), StatsFragment())

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount() = fragments.size

}