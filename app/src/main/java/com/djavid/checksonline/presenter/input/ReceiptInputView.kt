package com.djavid.checksonline.presenter.input

import com.djavid.checksonline.base.BaseView

interface ReceiptInputView: BaseView {

    fun showFailDialog()
    fun showSuccessDialog(receiptId: String)
    fun showWaitDialog()

}