package com.djavid.checksonline.view.root

import android.view.View
import androidx.fragment.app.FragmentManager
import com.djavid.checksonline.contracts.root.RootContract
import kotlinx.android.synthetic.main.activity_root.view.*

class RootView constructor(
		private val viewRoot: View,
        private val fm: FragmentManager
) : RootContract.View {

    private lateinit var presenter: RootContract.Presenter

    override fun init(presenter: RootContract.Presenter) {
        this.presenter = presenter

        viewRoot.root_viewPager.adapter = PlatesAdapter(fm)
        viewRoot.root_viewPager.setCurrentItem(PlatesAdapter.PAGE_CHECKS, false)
    }

}