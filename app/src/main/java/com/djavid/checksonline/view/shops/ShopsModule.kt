package com.djavid.checksonline.view.shops

import android.view.View
import kotlinx.android.synthetic.main.fragment_shops.*

@Module(includes = [Bindings::class])
class ShopsModule {

    @Provides
    fun provideHabitsViewRoot(fragment: ShopsFragment): View = fragment.shopsFragment

}

@Module
interface Bindings {

    @Binds
    fun bindHabitsPresenter(impl: ShopsPresenter): ShopsContract.Presenter

    @Binds
    fun bindHabitsView(view: ShopsView): ShopsContract.View

}