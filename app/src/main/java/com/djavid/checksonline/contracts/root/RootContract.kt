package com.djavid.checksonline.contracts.root

import com.djavid.checksonline.model.entities.Receipt

interface RootContract {

    interface Presenter {
        fun init()
        fun onQRSelected()
        fun onChecksSelected()
        fun onStatsSelected()
    }
	
	interface View {
		fun init(presenter: Presenter)
	}
	
	interface Navigator {
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