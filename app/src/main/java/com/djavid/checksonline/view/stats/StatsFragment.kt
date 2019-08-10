package com.djavid.checksonline.view.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseFragment
import org.kodein.di.generic.instance

class StatsFragment : BaseFragment() {
	
	private val presenter: StatsContract.Presenter by instance()

    override val layoutResId = R.layout.fragment_stats


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_stats, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
		//todo call component from activity
        presenter.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}
