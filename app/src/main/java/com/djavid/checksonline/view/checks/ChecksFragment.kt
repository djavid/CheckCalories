package com.djavid.checksonline.view.checks

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.app.KodeinApp
import com.djavid.checksonline.base.BaseFragment
import com.djavid.checksonline.contracts.checks.ChecksContract
import org.kodein.di.generic.instance

class ChecksFragment : BaseFragment() {
	
	private val presenter: ChecksContract.Presenter by instance()

    override val layoutResId = R.layout.fragment_checks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		kodein = (activity!!.application as KodeinApp).checksModule(this)
        presenter.init()
    }

}
