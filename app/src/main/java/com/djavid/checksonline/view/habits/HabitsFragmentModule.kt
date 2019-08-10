package com.djavid.checksonline.view.habits

import android.view.View
import com.djavid.checksonline.contracts.habits.HabitsContract
import com.djavid.checksonline.contracts.habits.NewHabitsPresenter
import kotlinx.android.synthetic.main.fragment_habits.*

@Module(includes = [Bindings::class])
class HabitsFragmentModule {

    @Provides
    fun provideHabitsViewRoot(fragment: HabitsFragment): View = fragment.habitsFragment

}

@Module
interface Bindings {

    @Binds
    fun bindHabitsPresenter(impl: NewHabitsPresenter): HabitsContract.Presenter

    @Binds
    fun bindHabitsView(view: NewHabitsView): HabitsContract.View

}