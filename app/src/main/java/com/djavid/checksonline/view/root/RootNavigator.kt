package com.djavid.checksonline.view.root

import android.content.Context
import android.content.Intent
import com.djavid.checksonline.contracts.root.RootContract

class RootNavigator(
		private val context: Context
) : RootContract.Navigator {
	
	override fun goToRoot() {
		val intent = Intent(context, RootActivity::class.java)
		context.startActivity(intent)
	}
	
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