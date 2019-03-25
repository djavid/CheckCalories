package com.djavid.checksonline.features.categories

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.features.base.NewBaseFragment
import com.djavid.checksonline.features.stats.EXTRA_DATE_INTERVAL
import com.djavid.checksonline.model.entities.DateInterval
import javax.inject.Inject

class CategoriesFragment : NewBaseFragment() {

    companion object {
        fun newInstance(): CategoriesFragment = CategoriesFragment()
    }

    @Inject
    lateinit var presenter: CategoriesContract.Presenter

    override val layoutResId = R.layout.fragment_categories


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
