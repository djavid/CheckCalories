package com.djavid.checksonline.features.root

import com.djavid.checksonline.features.qr.QrNavigator
import javax.inject.Inject

class RootPresenter @Inject constructor(
        private val view: RootContract.View,
        private val qrNavigator: QrNavigator
): RootContract.Presenter {

    override fun init() {
        view.init(this)
    }

    override fun onQRSelected() {
        qrNavigator.goToQrScreen()
    }

    override fun onChecksSelected() {
        //TODO navigator.goToChecksScreen()
    }

    override fun onStatsSelected() {
        //TODO navigator.goToStatsScreen()
    }
}