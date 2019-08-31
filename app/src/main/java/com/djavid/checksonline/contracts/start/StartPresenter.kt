package com.djavid.checksonline.contracts.start

import com.djavid.checksonline.contracts.auth.AuthContract
import com.djavid.checksonline.contracts.root.RootContract
import com.google.firebase.auth.FirebaseAuth

class StartPresenter(
		private val view: StartContract.View,
		private val authNavigator: AuthContract.Navigator,
		private val rootNavigator: RootContract.Navigator
) : StartContract.Presenter {
	
	override fun init() {
		view.init(this)
		checkAuth()
	}
	
	private fun checkAuth() {
		view.finish()
		if (userSignedIn())
			rootNavigator.goToRoot()
		else
			authNavigator.goToAuth()
	}
	
	private fun userSignedIn() = FirebaseAuth.getInstance().currentUser != null
	
}