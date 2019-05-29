package com.djavid.checksonline.features.checks

import android.content.Context
import android.view.ViewGroup
import com.djavid.checksonline.features.check.activity.CheckActivityContract
import com.djavid.checksonline.features.check.activity.CheckNavigator
import com.djavid.checksonline.features.root.OriginActivityContext
import com.djavid.checksonline.features.root.ViewRoot
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_checks.*

@Module(includes = [Bindings::class])
class ChecksModule {

    @Provides
    @ViewRoot
    fun provideChecksViewRoot(fragment: ChecksFragment): ViewGroup = fragment.panelChecks

    @Provides
    @OriginActivityContext
    fun provideContext(fragment: ChecksFragment): Context = fragment.context!!

    @Provides
    fun provideFragmentManager(fragment: ChecksFragment) = fragment.fragmentManager

}

@Module
interface Bindings {

    @Binds
    fun bindChecksPresenter(impl: ChecksPresenter): ChecksContract.Presenter

    @Binds
    fun bindChecksView(view: ChecksView): ChecksContract.View

    @Binds
    fun bindCheckNavigator(navigator: CheckNavigator): CheckActivityContract.Navigator

}