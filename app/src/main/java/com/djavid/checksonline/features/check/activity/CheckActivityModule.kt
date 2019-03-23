package com.djavid.checksonline.features.check.activity

import android.view.View
import com.djavid.checksonline.features.root.ViewRoot
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.activity_check.*

@Module(includes = [Bindings::class])
class CheckActivityModule {

    @Provides
    @ViewRoot
    fun provideViewRoot(activity: CheckActivity): View = activity.checkActivity

}

@Module
interface Bindings {

    @Binds
    fun bindCheckActivityPresenter(impl: CheckActivityPresenter): CheckActivityContract.Presenter

    @Binds
    fun bindQRView(view: CheckActivityView): CheckActivityContract.View

}