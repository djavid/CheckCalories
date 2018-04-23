package com.djavid.checksonline.presenter.qrcode

import com.djavid.checksonline.base.BaseView

interface QRCodeView : BaseView {

    //fun requestPermissions()
    fun initQrCodeReaderView()

    fun requestPermissions()

    fun showEmptyView(show: Boolean)

    fun stopCamera()

    fun startCamera()

    fun setDecodingEnabled(enabled: Boolean)

}