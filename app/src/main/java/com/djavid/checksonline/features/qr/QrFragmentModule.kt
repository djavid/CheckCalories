package com.djavid.checksonline.features.qr

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.djavid.checksonline.dagger.UIScope
import com.djavid.checksonline.features.root.OriginActivityContext
import com.djavid.checksonline.features.root.ViewRoot
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.activity_qrcode.*

@Module(includes = [Bindings::class])
class QRFragmentModule {

    @Provides
    @ViewRoot
    fun provideViewRoot(fragment: QrFragment): View = fragment.qr_container

    @Provides
    fun provideFragment(fragment: QrFragment): Fragment = fragment

    @Provides
    @OriginActivityContext
    fun provideUIContext(fragment: QrFragment): Context? = fragment.context

    @Provides
    @UIScope
    fun provideLifecycle(fragment: QrFragment): Lifecycle = fragment.lifecycle

}

@Module
interface Bindings {

    @Binds
    fun bindQRPresenter(impl: QrPresenter): QrContract.Presenter

    @Binds
    fun bindQRView(view: QrView): QrContract.View

    @Binds
    fun bindNavigator(navigator: QrNavigator): QrContract.Navigator

}