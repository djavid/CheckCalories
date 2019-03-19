package com.djavid.checksonline.features.qrcode

import com.djavid.checksonline.features.base.NewBasePresenter

interface QRContract {

    abstract class Presenter : NewBasePresenter() {
        abstract fun onQrCodeRead(text: String)
        abstract fun onScanBtnClick()
        abstract fun onManualInputBtnClick()
        abstract fun onOpenButtonClicked(receiptId: String)
    }

    interface View {
        fun init(presenter: Presenter)

        fun requestPermissions()
        fun showEmptyView(show: Boolean)
        fun resumeScanning()
        fun stopScanning()

        fun playBeep()
        fun vibrate()

        fun showFailDialog()
        fun showSuccessDialog(receiptId: String)
        fun showWaitDialog()
        fun onPause()
        fun onResume()
        fun showProgress(show: Boolean)
    }
}