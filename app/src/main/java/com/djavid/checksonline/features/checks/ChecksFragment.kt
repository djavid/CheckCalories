package com.djavid.checksonline.features.checks

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.features.base.NewBaseFragment
import javax.inject.Inject

class ChecksFragment : NewBaseFragment() {

    companion object {
        fun newInstance(): ChecksFragment = ChecksFragment()
    }

    @Inject
    lateinit var presenter: ChecksContract.Presenter

    override val layoutResId get() = R.layout.fragment_checks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.init()
    }

}
