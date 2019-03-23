package com.djavid.checksonline.features.shops

import android.view.View
import com.djavid.checksonline.features.root.ViewRoot
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_shops.*

@Module(includes = [Bindings::class])
class ShopsModule {

    @Provides
    @ViewRoot
    fun provideHabitsViewRoot(fragment: ShopsFragment): View = fragment.shopsFragment

}

@Module
interface Bindings {

    @Binds
    fun bindHabitsPresenter(impl: ShopsPresenter): ShopsContract.Presenter

    @Binds
    fun bindHabitsView(view: ShopsView): ShopsContract.View

}