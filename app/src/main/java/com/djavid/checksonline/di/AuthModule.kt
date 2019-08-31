package com.djavid.checksonline.di

import android.app.Activity
import android.content.Context
import android.view.View
import com.djavid.checksonline.contracts.auth.AuthContract
import com.djavid.checksonline.contracts.auth.AuthPresenter
import com.djavid.checksonline.utils.MODULE_AUTH
import com.djavid.checksonline.utils.MODULE_AUTH_NAVIGATOR
import com.djavid.checksonline.view.auth.AuthNavigator
import com.djavid.checksonline.view.auth.AuthView
import kotlinx.android.synthetic.main.activity_auth.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class AuthModule(activity: Activity) {
	val kodein = Kodein.Module(MODULE_AUTH) {
		bind<View>() with singleton { activity.authActivity }
		
		bind<AuthContract.Presenter>() with singleton { AuthPresenter(instance(), instance()) }
		
		bind<AuthContract.View>() with singleton { AuthView(instance()) }
	}
}

class AuthNavigatorModule(context: Context) {
	val kodein = Kodein.Module(MODULE_AUTH_NAVIGATOR) {
		bind<AuthContract.Navigator>() with singleton { AuthNavigator(context) }
	}
}