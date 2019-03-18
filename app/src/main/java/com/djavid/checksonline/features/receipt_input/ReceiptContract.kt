package com.djavid.checksonline.features.receipt_input

import com.djavid.checksonline.model.networking.bodies.FnsValues

interface ReceiptContract {

    interface Presenter {
        fun init()
        fun onOpenButtonClicked(receiptId: String)
        fun sendCheck(fnsValues: FnsValues)
        fun onBackPressed()
        fun onDestroy()
    }

    interface View {
        fun init(presenter: Presenter)
        fun showFailDialog()
        fun showSuccessDialog(receiptId: String)
        fun showWaitDialog()
        fun showProgress(show: Boolean)
    }

    enum class Dialog { SUCCESS, FAIL, WAIT }

}