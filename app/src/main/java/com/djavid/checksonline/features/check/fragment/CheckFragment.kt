package com.djavid.checksonline.features.check.fragment

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.features.app.EXTRA_CHECK_ID
import com.djavid.checksonline.features.base.NewBaseFragment
import javax.inject.Inject

class CheckFragment : NewBaseFragment() {

    companion object {
        fun newInstance(): CheckFragment = CheckFragment()
    }

    @Inject
    lateinit var presenter: CheckFragmentContract.Presenter

    override val layoutResId get() = R.layout.fragment_check


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getString(EXTRA_CHECK_ID)?.let {
            presenter.init(it)
        }
    }
}
