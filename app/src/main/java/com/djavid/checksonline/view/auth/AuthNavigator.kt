package com.djavid.checksonline.view.auth

import android.content.Context
import android.content.Intent
import com.djavid.checksonline.contracts.auth.AuthContract

class AuthNavigator(
		private val context: Context
) : AuthContract.Navigator {
	
	override fun goToAuth() {
		val intent = Intent(context, AuthActivity::class.java)
		context.startActivity(intent)
	}
	
}