package com.djavid.checksonline.features.check.fragment

import android.view.View
import com.djavid.checksonline.features.root.ViewRoot
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_check.*

@Module(includes = [Bindings::class])
class CheckFragmentModule {

    @Provides
    @ViewRoot
    fun provideCheckViewRoot(fragment: CheckFragment): View = fragment.checkFragment

}

@Module
interface Bindings {

    @Binds
    fun bindCheckPresenter(impl: CheckPresenter): CheckFragmentContract.Presenter

    @Binds
    fun bindCHeckView(view: CheckView): CheckFragmentContract.View

}