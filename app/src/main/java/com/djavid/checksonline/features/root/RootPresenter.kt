package com.djavid.checksonline.features.root

import javax.inject.Inject

class RootPresenter @Inject constructor(
        private val view: RootContract.View,
        private val navigator: RootContract.Navigation
): RootContract.Presenter {

    override fun init() {
        view.init(this)
    }

    override fun onQRSelected() {
        navigator.goToQrScreen()
    }

    override fun onChecksSelected() {
        navigator.goToChecksScreen()
    }

    override fun onStatsSelected() {
        navigator.goToStatsScreen()
    }
}