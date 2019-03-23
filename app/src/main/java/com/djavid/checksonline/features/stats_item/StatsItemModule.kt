package com.djavid.checksonline.features.stats_item

import android.view.View
import com.djavid.checksonline.features.root.ViewRoot
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_stat_item.*

@Module(includes = [Bindings::class])
class StatItemModule {

    @Provides
    @ViewRoot
    fun provideStatItemViewRoot(fragment: StatItemFragment): View = fragment.statItemFragment

}

@Module
interface Bindings {

    @Binds
    fun bindStatItemPresenter(impl: StatsItemPresenter): StatsItemContract.Presenter

    @Binds
    fun bindStatItemView(view: StatsItemView): StatsItemContract.View

}