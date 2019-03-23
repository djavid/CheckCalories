package com.djavid.checksonline.features.check.activity

class CheckActivityContract {

    interface View {
        fun init(presenter: Presenter)
    }

    interface Presenter {
        fun init(checkId: String)
    }
}