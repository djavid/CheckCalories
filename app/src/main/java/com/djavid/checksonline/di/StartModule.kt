package com.djavid.checksonline.di

import android.app.Activity
import android.view.View
import com.djavid.checksonline.contracts.start.StartContract
import com.djavid.checksonline.contracts.start.StartPresenter
import com.djavid.checksonline.utils.MODULE_START
import com.djavid.checksonline.view.start.StartView
import kotlinx.android.synthetic.main.activity_start.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class StartModule(activity: Activity) {
	val kodein = Kodein.Module(MODULE_START) {
		bind<View>() with singleton { activity.startActivity }
		
		bind<StartContract.Presenter>() with singleton { StartPresenter(instance(), instance(), instance()) }
		
		bind<StartContract.View>() with singleton { StartView(instance()) }
	}
}