package com.djavid.checksonline.features.root

import android.view.View
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_root.view.*
import javax.inject.Inject

class RootView @Inject constructor(
        @ViewRoot private val viewRoot: View,
        private val fm: FragmentManager
) : RootContract.View {

    private lateinit var presenter: RootContract.Presenter

    override fun init(presenter: RootContract.Presenter) {
        this.presenter = presenter

        viewRoot.root_viewPager.adapter = PlatesAdapter(fm)
        viewRoot.root_viewPager.setCurrentItem(PlatesAdapter.PAGE_CHECKS, false)
    }

}