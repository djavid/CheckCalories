package com.djavid.checksonline.features.root

import javax.inject.Inject

class NewRootPresenter @Inject constructor(
        private val view: RootContract.View,
        private val navigator: RootContract.Navigation
): RootContract.Presenter {

    override fun init() {
        view.init(this)
    }

    fun onQRSelected() {
        navigator.goToQrScreen()
    }

    fun onChecksSelected() {
        navigator.goToChecksScreen()
    }

    fun onStatsSelected() {
        navigator.goToStatsScreen()
    }
}