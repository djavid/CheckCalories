package com.djavid.checksonline.features.root

interface RootContract {

    interface View {
        fun init(presenter: RootContract.Presenter)
    }

    interface Presenter {
        fun init()
        fun onQRSelected()
        fun onChecksSelected()
        fun onStatsSelected()
    }

    interface Navigation {
        fun goToQrScreen()
        fun goToChecksScreen()
        fun goToStatsScreen()
    }

}