package com.djavid.checksonline.features.root

import com.djavid.checksonline.model.entities.Receipt

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

    interface BottomSheet {
        fun showSheet()
        fun hideSheet()
        fun openCheck(receipt: Receipt)
    }

}