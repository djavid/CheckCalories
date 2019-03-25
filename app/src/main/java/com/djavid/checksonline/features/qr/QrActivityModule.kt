package com.djavid.checksonline.features.qr

import android.content.Context
import android.view.View
import com.djavid.checksonline.features.root.OriginActivityContext
import com.djavid.checksonline.features.root.ViewRoot
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.activity_qrcode.*

@Module(includes = [Bindings::class])
class QRCodeActivityModule {

    @Provides
    @ViewRoot
    fun provideViewRoot(activity: QrActivity): View = activity.qrcodeActivity

    @Provides
    @OriginActivityContext
    fun provideUIContext(activity: QrActivity): Context = activity

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