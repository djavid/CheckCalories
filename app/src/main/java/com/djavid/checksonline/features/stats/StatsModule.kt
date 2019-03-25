package com.djavid.checksonline.features.stats

import android.support.v4.app.Fragment
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
    fun provideFragment(fragment: StatsFragment): Fragment = fragment

}

@Module
interface Bindings {

    @Binds
    fun bindStatsPresenter(impl: StatsPresenter): StatsContract.Presenter

    @Binds
    fun bindStatsView(view: StatsView): StatsContract.View

}