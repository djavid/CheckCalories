package com.djavid.checksonline.features.qr

import android.view.View
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

}

@Module
interface Bindings {

    @Binds
    fun bindQRPresenter(impl: QrPresenter): QrContract.Presenter

    @Binds
    fun bindQRView(view: QrView): QrContract.View

}