package com.djavid.checksonline.features.check_new

import android.view.ViewGroup
import com.djavid.checksonline.features.root.ViewRoot
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.receipt_sliding_panel.*

@Module(includes = [Bindings::class])
class NewCheckModule {

    @Provides
    @ViewRoot
    fun provideCheckViewRoot(fragment: NewCheckFragment): ViewGroup = fragment.sliding_layout

}

@Module
interface Bindings {

    @Binds
    fun bindCheckPresenter(impl: NewCheckPresenter): NewCheckContract.Presenter

    @Binds
    fun bindCheckView(view: NewCheckView): NewCheckContract.View

}