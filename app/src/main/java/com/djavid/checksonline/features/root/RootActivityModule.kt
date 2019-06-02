package com.djavid.checksonline.features.root

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.djavid.checksonline.features.check_new.BottomSheetNavigator
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

    @Provides
    fun provideActivityLifecycle(activity: RootActivity): Lifecycle = activity.lifecycle

    @Provides
    fun provideFragmentManager(activity: RootActivity): FragmentManager = activity.supportFragmentManager

    @Provides
    @BottomSheet
    fun provideBottomSheet(activity: RootActivity): ViewGroup = activity.root_bottomSheet

}

@Module
interface Bindings {

    @Binds
    fun bindRootPresenter(impl: RootPresenter): RootContract.Presenter

    @Binds
    fun bindView(impl: RootView): RootContract.View

    @Binds
    fun bindRootNavigation(impl: RootNavigation): RootContract.Navigation

    @Binds
    fun bindBottomSheet(impl: BottomSheetNavigator): RootContract.BottomSheet

}

@Qualifier
annotation class ViewRoot

@Qualifier
annotation class OriginActivityContext

@Qualifier
annotation class BottomSheet