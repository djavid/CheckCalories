package com.djavid.checksonline.view.receipt_input

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.app.KodeinApp
import com.djavid.checksonline.base.BaseFragment
import com.djavid.checksonline.contracts.check_input.CheckInputContract
import org.kodein.di.generic.instance

class CheckInputFragment : BaseFragment() {
	
	private val presenter: CheckInputContract.Presenter by instance()

    override val layoutResId = R.layout.fragment_receipt_input

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		(activity!!.application as KodeinApp).checkInputModule(this)
        presenter.init()
    }
	
}
