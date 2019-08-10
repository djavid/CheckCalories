package com.djavid.checksonline.di

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.djavid.checksonline.contracts.qr.QrContract
import com.djavid.checksonline.contracts.qr.QrPresenter
import com.djavid.checksonline.view.qr.QrFragment
import com.djavid.checksonline.view.qr.QrView
import kotlinx.android.synthetic.main.activity_qrcode.*

@Module(includes = [Bindings::class])
class QRFragmentModule {

    @Provides
    fun provideViewRoot(fragment: QrFragment): View = fragment.qr_container

    @Provides
    fun provideFragment(fragment: QrFragment): Fragment = fragment

    @Provides
    fun provideUIContext(fragment: QrFragment): Context? = fragment.context

    @Provides
    fun provideLifecycle(fragment: QrFragment): Lifecycle = fragment.lifecycle

}

@Module
interface Bindings {

    @Binds
    fun bindQRPresenter(impl: QrPresenter): QrContract.Presenter

    @Binds
    fun bindQRView(view: QrView): QrContract.View

}