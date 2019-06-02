package com.djavid.checksonline.features.check_new

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.features.app.EXTRA_RECEIPT
import com.djavid.checksonline.features.base.NewBaseFragment
import com.djavid.checksonline.model.entities.Receipt
import javax.inject.Inject

class NewCheckFragment : NewBaseFragment() {

    override val layoutResId: Int = R.layout.fragment_check

    @Inject
    lateinit var presenter: NewCheckContract.Presenter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init()

        arguments?.getSerializable(EXTRA_RECEIPT)?.let {
            presenter.showReceipt(it as Receipt)
        }
    }

}