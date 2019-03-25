package com.djavid.checksonline.features.root

import android.content.Context
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

    @Provides
    @OriginActivityContext
    fun provideActivity(activity: RootActivity): Context = activity

}

@Module
interface Bindings {

    @Binds
    fun bindRootPresenter(impl: RootPresenter): RootContract.Presenter

    @Binds
    fun bindView(impl: RootView): RootContract.View

    @Binds
    fun bindRootNavigation(impl: RootNavigation): RootContract.Navigation

}

@Qualifier
annotation class ViewRoot

@Qualifier
annotation class OriginActivityContext