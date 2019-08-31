package com.djavid.checksonline.view.start

import android.app.Activity
import android.view.View
import com.djavid.checksonline.contracts.start.StartContract

class StartView(
		private val viewRoot: View
) : StartContract.View {
	
	private lateinit var presenter: StartContract.Presenter
	
	override fun init(presenter: StartContract.Presenter) {
		this.presenter = presenter
	}
	
	override fun finish() {
		(viewRoot.context as Activity).finish()
	}
	
}