package com.djavid.checksonline.view.auth

import android.os.Bundle
import com.djavid.checksonline.R
import com.djavid.checksonline.app.KodeinApp
import com.djavid.checksonline.base.BaseActivity
import com.djavid.checksonline.contracts.auth.AuthContract
import org.kodein.di.generic.instance

class AuthActivity : BaseActivity() {
	
	private val presenter: AuthContract.Presenter by instance()
	
	override val layoutResId = R.layout.activity_auth
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		kodein = (application as KodeinApp).authModule(this)
		presenter.init()
	}
	
}