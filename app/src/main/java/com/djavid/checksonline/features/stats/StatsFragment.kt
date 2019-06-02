package com.djavid.checksonline.features.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.djavid.checksonline.R
import com.djavid.checksonline.features.base.NewBaseFragment
import javax.inject.Inject

class StatsFragment : NewBaseFragment() {

    @Inject
    lateinit var presenter: StatsContract.Presenter

    override val layoutResId = R.layout.fragment_stats


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_stats, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}
