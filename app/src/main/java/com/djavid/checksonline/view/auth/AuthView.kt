package com.djavid.checksonline.view.auth

import android.app.Activity
import android.view.View
import com.djavid.checksonline.contracts.auth.AuthContract
import kotlinx.android.synthetic.main.activity_auth.view.*

class AuthView(
		private val viewRoot: View
) : AuthContract.View {
	
	private lateinit var presenter: AuthContract.Presenter
	
	override fun init(presenter: AuthContract.Presenter) {
		this.presenter = presenter
		
		viewRoot.startBtn.setOnClickListener {
			presenter.onAuthClicked()
		}
	}
	
	override fun finish() {
		(viewRoot.context as Activity).finish()
	}
	
}