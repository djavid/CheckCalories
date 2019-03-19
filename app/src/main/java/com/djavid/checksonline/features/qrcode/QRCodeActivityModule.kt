package com.djavid.checksonline.features.qrcode

import android.view.View
import com.djavid.checksonline.features.root.ViewRoot
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.activity_qrcode.*

@Module
class QRCodeActivityModule {

    @Provides
    @ViewRoot
    fun provideViewRoot(activity: QRActivity): View = activity.qrcodeActivity

}

@Module
interface Bindings {

    @Binds
    fun bindQRPresenter(impl: QRCodePresenter): QRContract.Presenter

    @Binds
    fun bindQRView(view: NQRCodeView): QRContract.View

}