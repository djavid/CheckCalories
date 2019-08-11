package com.djavid.checksonline.view.shops

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseFragment
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.view.stats.EXTRA_DATE_INTERVAL
import org.kodein.di.generic.instance

class ShopsFragment : BaseFragment() {
	
	private val presenter: ShopsContract.Presenter by instance()

    override val layoutResId = R.layout.fragment_shops

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		//(activity!!.application as KodeinApp).shopsModule(this)
	
		val dateInterval = arguments?.getParcelable<DateInterval>(EXTRA_DATE_INTERVAL)
		dateInterval?.let { presenter.init(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}
