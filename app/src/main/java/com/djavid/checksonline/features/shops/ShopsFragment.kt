package com.djavid.checksonline.features.shops

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.features.base.NewBaseFragment
import com.djavid.checksonline.features.stats.EXTRA_DATE_INTERVAL
import com.djavid.checksonline.model.entities.DateInterval
import javax.inject.Inject

class ShopsFragment : NewBaseFragment() {

    companion object {
        fun newInstance(): ShopsFragment = ShopsFragment()
    }

    @Inject
    lateinit var presenter: ShopsContract.Presenter

    override val layoutResId = R.layout.fragment_shops


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getParcelable<DateInterval>(EXTRA_DATE_INTERVAL)?.let {
            presenter.init(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}
