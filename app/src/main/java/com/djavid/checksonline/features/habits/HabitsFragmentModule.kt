package com.djavid.checksonline.features.habits

import android.view.View
import com.djavid.checksonline.features.root.ViewRoot
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_habits.*

@Module(includes = [Bindings::class])
class HabitsFragmentModule {

    @Provides
    @ViewRoot
    fun provideHabitsViewRoot(fragment: HabitsFragment): View = fragment.habitsFragment

}

@Module
interface Bindings {

    @Binds
    fun bindHabitsPresenter(impl: NewHabitsPresenter): HabitsContract.Presenter

    @Binds
    fun bindHabitsView(view: NewHabitsView): HabitsContract.View

}