package com.djavid.checksonline.view.check

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.app.KodeinApp
import com.djavid.checksonline.base.BaseFragment
import com.djavid.checksonline.contracts.check.CheckContract
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.utils.EXTRA_RECEIPT
import org.kodein.di.generic.instance

class CheckFragment : BaseFragment() {
	
	override val layoutResId: Int = R.layout.fragment_check
	
	private val presenter: CheckContract.Presenter by instance()
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		kodein = (activity!!.application as KodeinApp).checkModule(this)
		presenter.init("")
		
		arguments?.getSerializable(EXTRA_RECEIPT)?.let {
			presenter.showReceipt(it as Receipt)
		}
	}
	
}