package com.djavid.checksonline.view.stats_item

import android.view.View
import kotlinx.android.synthetic.main.fragment_stat_item.*

@Module(includes = [Bindings::class])
class StatItemModule {

    @Provides
    fun provideStatItemViewRoot(fragment: StatItemFragment): View = fragment.statItemFragment

}

@Module
interface Bindings {

    @Binds
    fun bindStatItemPresenter(impl: StatsItemPresenter): StatsItemContract.Presenter

    @Binds
    fun bindStatItemView(view: StatsItemView): StatsItemContract.View

}