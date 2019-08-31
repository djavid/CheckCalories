package com.djavid.checksonline.contracts.auth

import com.djavid.checksonline.contracts.root.RootContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthPresenter(
		private val view: AuthContract.View,
		private val rootNavigator: RootContract.Navigator
) : AuthContract.Presenter {
	
	override fun init() {
		view.init(this)
	}
	
	override fun onAuthClicked() {
		val auth = FirebaseAuth.getInstance()
		
		auth.signInAnonymously().addOnCompleteListener { result ->
			if (result.isSuccessful) {
				val user = auth.currentUser
				user?.let { retrieveToken(it) }
			} else {
				//todo unsuccessful auth
			}
		}
	}
	
	private fun retrieveToken(user: FirebaseUser) {
		user.getIdToken(true).addOnCompleteListener {
			if (it.isSuccessful) {
				val token = it.result?.token //todo send token to server
				
				view.finish()
				rootNavigator.goToRoot() //todo finish all activities before
			} else {
				//todo error retrieving token
			}
		}
	}
	
}