package com.djavid.checksonline.features.QRCode

import com.djavid.checksonline.features.base.BaseView

interface QRCodeView : BaseView {

    fun requestPermissions()
    fun showEmptyView(show: Boolean)
    fun resumeScanning()
    fun stopScanning()

    fun playBeep()
    fun vibrate()

    fun showFailDialog()
    fun showSuccessDialog(receiptId: String)
    fun showWaitDialog()

}