package com.djavid.checksonline.features.stats

import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.view.View
import com.djavid.checksonline.features.root.ViewRoot
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_stats.*

@Module(includes = [Bindings::class])
class StatsModule {

    @Provides
    @ViewRoot
    fun provideStatsViewRoot(fragment: StatsFragment): View = fragment.fragmentStats

    @Provides
    @ViewRoot
    fun provideFragmentManager(fragment: StatsFragment): FragmentManager? = fragment.childFragmentManager

    @Provides
    fun provideActivity(fragment: StatsFragment): FragmentActivity? = fragment.activity

}

@Module
interface Bindings {

    @Binds
    fun bindHabitsPresenter(impl: StatsPresenter): StatsContract.Presenter

    @Binds
    fun bindHabitsView(view: StatsView): StatsContract.View

}