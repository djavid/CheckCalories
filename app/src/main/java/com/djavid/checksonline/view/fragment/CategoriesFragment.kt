package com.djavid.checksonline.view.fragment

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseFragment
import com.djavid.checksonline.presenter.stats.CategoriesPresenter
import com.djavid.checksonline.presenter.stats.CategoriesView
import toothpick.Toothpick

class CategoriesFragment : BaseFragment(), CategoriesView {

    companion object {
        fun newInstance(): CategoriesFragment = CategoriesFragment()
    }

    @InjectPresenter
    lateinit var presenter: CategoriesPresenter

    @ProvidePresenter
    fun providePresenter(): CategoriesPresenter =
            Toothpick.openScopes(activity, this).getInstance(CategoriesPresenter::class.java)

    override val layoutResId = R.layout.fragment_categories


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, Toothpick.openScopes(activity, this))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) Toothpick.closeScope(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}
