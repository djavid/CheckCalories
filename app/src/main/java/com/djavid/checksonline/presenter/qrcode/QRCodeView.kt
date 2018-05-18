package com.djavid.checksonline.presenter.qrcode

import com.djavid.checksonline.base.BaseView

interface QRCodeView : BaseView {

    fun requestPermissions()
    fun showEmptyView(show: Boolean)
    fun resumeScanning()
    fun stopScanning()

    fun playBeep()
    fun vibrate()

    fun showFailDialog()
    fun showSuccessDialog(receiptId: String)

}