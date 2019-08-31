package com.djavid.checksonline.view.start

import android.os.Bundle
import com.djavid.checksonline.R
import com.djavid.checksonline.app.KodeinApp
import com.djavid.checksonline.base.BaseActivity
import com.djavid.checksonline.contracts.start.StartContract
import org.kodein.di.generic.instance

class StartActivity : BaseActivity() {
	
	private val presenter: StartContract.Presenter by instance()
	
	override val layoutResId = R.layout.activity_start
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		kodein = (application as KodeinApp).startModule(this)
		presenter.init()
	}
}