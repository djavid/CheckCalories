package com.djavid.checksonline.features.root

import javax.inject.Inject

class RootNavigation @Inject constructor() : RootContract.Navigation {

    override fun goToQrScreen() {
        println("RootNavigation: goToQrScreen")
    }

    override fun goToChecksScreen() {
        println("RootNavigation: goToChecksScreen")
    }

    override fun goToStatsScreen() {
        println("RootNavigation: goToStatsScreen")
    }
}