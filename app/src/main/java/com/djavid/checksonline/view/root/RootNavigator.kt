package com.djavid.checksonline.view.root

import com.djavid.checksonline.contracts.root.RootContract

class RootNavigator : RootContract.Navigator {
	
	override fun goToQrScreen() {
		println("RootNavigator: goToQrScreen")
	}
	
	override fun goToChecksScreen() {
		println("RootNavigator: goToChecksScreen")
	}
	
	override fun goToStatsScreen() {
		println("RootNavigator: goToStatsScreen")
	}
	
}