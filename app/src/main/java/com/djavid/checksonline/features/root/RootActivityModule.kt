package com.djavid.checksonline.features.root

import android.view.View
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.activity_root.*
import javax.inject.Qualifier

@Module(includes = [Bindings::class])
class RootActivityModule {

    @Provides
    @ViewRoot
    fun provideRootView(activity: RootActivity): View = activity.rootActivity

}

@Module
interface Bindings {

    @Binds
    fun bindRootPresenter(impl: RootPresenter): RootContract.Presenter

}

@Qualifier
annotation class ViewRoot