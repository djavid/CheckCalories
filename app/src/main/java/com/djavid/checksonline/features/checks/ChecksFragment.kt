package com.djavid.checksonline.features.checks

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.features.base.NewBaseFragment
import javax.inject.Inject

class ChecksFragment : NewBaseFragment() {

    @Inject
    lateinit var presenter: ChecksContract.Presenter

    override val layoutResId = R.layout.fragment_checks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init()
    }

}
