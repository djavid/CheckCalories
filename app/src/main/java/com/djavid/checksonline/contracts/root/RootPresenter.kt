package com.djavid.checksonline.contracts.root

class RootPresenter constructor(
		private val view: RootContract.View
): RootContract.Presenter {

    override fun init() {
        view.init(this)
    }

    override fun onQRSelected() {
		//qrNavigator.goToQrScreen()
    }

    override fun onChecksSelected() {
        //TODO navigator.goToChecksScreen()
    }

    override fun onStatsSelected() {
        //TODO navigator.goToStatsScreen()
    }
}