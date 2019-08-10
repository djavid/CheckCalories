package com.djavid.checksonline.view.shops

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseFragment
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.view.stats.EXTRA_DATE_INTERVAL

class ShopsFragment : BaseFragment() {

    companion object {
        fun newInstance(): ShopsFragment = ShopsFragment()
    }
	
	lateinit var presenter: ShopsContract.Presenter

    override val layoutResId = R.layout.fragment_shops


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getParcelable<DateInterval>(EXTRA_DATE_INTERVAL)?.let {
            presenter.init(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}
