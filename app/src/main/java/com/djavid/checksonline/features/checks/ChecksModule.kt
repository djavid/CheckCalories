package com.djavid.checksonline.features.checks

import android.content.Context
import android.view.View
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
    fun provideChecksViewRoot(fragment: ChecksFragment): View = fragment.checksFragment

    @Provides
    @OriginActivityContext
    fun provideContext(fragment: ChecksFragment): Context = fragment.context!!

}

@Module
interface Bindings {

    @Binds
    fun bindChecksPresenter(impl: ChecksPresenter): ChecksContract.Presenter

    @Binds
    fun bindChecksView(view: ChecksView): ChecksContract.View

}