package com.djavid.checksonline.features.check_new

interface NewCheckContract {

    interface Presenter {
        fun init()
    }

    interface View {
        fun init(presenter: Presenter)
    }

    interface Navigator {
        fun goToCheckScreen()
    }

}