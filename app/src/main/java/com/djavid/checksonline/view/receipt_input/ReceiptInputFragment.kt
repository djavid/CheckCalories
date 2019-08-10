package com.djavid.checksonline.view.receipt_input

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseFragment

class ReceiptInputFragment : BaseFragment() {

    companion object {
        fun newInstance(): ReceiptInputFragment = ReceiptInputFragment()
    }
	
	lateinit var presenter: ReceiptContract.Presenter

    override val layoutResId = R.layout.fragment_receipt_input

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
