package com.djavid.checksonline.features.check_new

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.features.base.NewBaseFragment
import javax.inject.Inject

class NewCheckFragment : NewBaseFragment() {

    override val layoutResId: Int = R.layout.receipt_sliding_panel

    @Inject
    lateinit var presenter: NewCheckContract.Presenter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}