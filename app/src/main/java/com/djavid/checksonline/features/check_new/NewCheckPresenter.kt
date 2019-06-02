package com.djavid.checksonline.features.check_new

import com.djavid.checksonline.model.entities.Receipt
import javax.inject.Inject

class NewCheckPresenter @Inject constructor(
        private val view: NewCheckContract.View
) : NewCheckContract.Presenter {

    override fun init() {
        view.init(this)
    }

    override fun showReceipt(receipt: Receipt) {
        view.showReceipt(receipt)
    }
}