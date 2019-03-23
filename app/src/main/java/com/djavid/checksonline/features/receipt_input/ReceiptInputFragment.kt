package com.djavid.checksonline.features.receipt_input

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.features.base.NewBaseFragment
import javax.inject.Inject

class ReceiptInputFragment : NewBaseFragment() {

    companion object {
        fun newInstance(): ReceiptInputFragment = ReceiptInputFragment()
    }

    @Inject
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
