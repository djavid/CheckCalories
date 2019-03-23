package com.djavid.checksonline.features.categories

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.features.base.NewBaseFragment
import javax.inject.Inject

class CategoriesFragment : NewBaseFragment() {

    companion object {
        fun newInstance(): CategoriesFragment = CategoriesFragment()
    }

    @Inject
    lateinit var presenter: CategoriesContract.Presenter

    override val layoutResId = R.layout.fragment_categories


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}
