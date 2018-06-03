package com.djavid.checksonline.view.fragment

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseFragment
import com.djavid.checksonline.presenter.stats.ShopsPresenter
import com.djavid.checksonline.presenter.stats.ShopsView
import toothpick.Toothpick

class ShopsFragment : BaseFragment(), ShopsView {

    companion object {
        fun newInstance(): ShopsFragment = ShopsFragment()
    }

    @InjectPresenter
    lateinit var presenter: ShopsPresenter

    @ProvidePresenter
    fun providePresenter(): ShopsPresenter =
            Toothpick.openScopes(activity, this).getInstance(ShopsPresenter::class.java)

    override val layoutResId = R.layout.fragment_shops


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
