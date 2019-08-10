package com.djavid.checksonline.view.categories

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseFragment
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.view.stats.EXTRA_DATE_INTERVAL
import org.kodein.di.generic.instance

class CategoriesFragment : BaseFragment() {

    companion object {
        fun newInstance(): CategoriesFragment = CategoriesFragment()
    }
    
    private val presenter: CategoriesContract.Presenter by instance()

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
