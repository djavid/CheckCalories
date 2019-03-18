package com.djavid.checksonline.features.receipt_input

import com.djavid.checksonline.features.base.BaseView

interface ReceiptInputView: BaseView {

    fun showFailDialog()
    fun showSuccessDialog(receiptId: String)
    fun showWaitDialog()

}