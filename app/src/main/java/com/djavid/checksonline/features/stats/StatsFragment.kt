package com.djavid.checksonline.features.stats

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.features.base.NewBaseFragment
import javax.inject.Inject

class StatsFragment : NewBaseFragment() {

    companion object {
        fun newInstance(): StatsFragment = StatsFragment()
    }

    @Inject
    lateinit var presenter: StatsContract.Presenter

    override val layoutResId = R.layout.fragment_stats


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}
