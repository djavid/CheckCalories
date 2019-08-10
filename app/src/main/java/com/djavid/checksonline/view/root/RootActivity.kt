package com.djavid.checksonline.view.root

import android.os.Bundle
import com.djavid.checksonline.R
import com.djavid.checksonline.app.KodeinApp
import com.djavid.checksonline.base.BaseActivity
import com.djavid.checksonline.contracts.root.RootContract
import org.kodein.di.generic.instance

class RootActivity : BaseActivity() {
	
	private val presenter: RootContract.Presenter by instance()
	
	override val layoutResId: Int = R.layout.activity_root
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		kodein = (application as KodeinApp).rootModule(this)
		presenter.init()
	}
	
}
