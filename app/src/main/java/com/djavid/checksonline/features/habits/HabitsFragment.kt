package com.djavid.checksonline.features.habits

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.features.base.NewBaseFragment
import javax.inject.Inject

class HabitsFragment : NewBaseFragment() {

    companion object {
        fun newInstance(): HabitsFragment = HabitsFragment()
    }

    @Inject
    lateinit var newPresenter: HabitsContract.Presenter

    override val layoutResId = R.layout.fragment_habits

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        newPresenter.init()
    }

}
