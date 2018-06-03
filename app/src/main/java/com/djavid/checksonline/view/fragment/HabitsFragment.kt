package com.djavid.checksonline.view.fragment

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseFragment
import com.djavid.checksonline.presenter.habits.HabitsPresenter
import com.djavid.checksonline.presenter.habits.HabitsView
import toothpick.Toothpick

class HabitsFragment : BaseFragment(), HabitsView {

    companion object {
        fun newInstance(): HabitsFragment = HabitsFragment()
    }

    @InjectPresenter
    lateinit var presenter: HabitsPresenter

    @ProvidePresenter
    fun providePresenter(): HabitsPresenter =
            Toothpick.openScopes(activity, this).getInstance(HabitsPresenter::class.java)

    override val layoutResId = R.layout.fragment_habits


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
