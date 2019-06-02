package com.djavid.checksonline.features.check_new

import com.djavid.checksonline.model.entities.Receipt

interface NewCheckContract {

    interface Presenter {
        fun init()
        fun showReceipt(receipt: Receipt)
    }

    interface View {
        fun init(presenter: Presenter)
        fun showReceipt(receipt: Receipt)
    }

    interface Navigator {
        fun goToCheckScreen()
    }

}