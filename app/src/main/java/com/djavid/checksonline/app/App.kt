package com.djavid.checksonline.app

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.djavid.checksonline.di.AppModule
import com.djavid.checksonline.di.CheckModule
import com.djavid.checksonline.di.ChecksModule
import com.djavid.checksonline.di.NetworkModule
import com.djavid.checksonline.di.RootModule
import com.djavid.checksonline.di.ThreadingModule
import net.danlew.android.joda.JodaTimeAndroid
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import timber.log.Timber

class App : Application(), KodeinAware, KodeinApp {
	
	override fun onCreate() {
		super.onCreate()
		Timber.plant(Timber.DebugTree())
		JodaTimeAndroid.init(this)
	}
	
	override val kodein by Kodein.lazy {
		//import(androidXModule(this@App))
		import(AppModule(this@App).kodein)
		import(NetworkModule().kodein)
		import(ThreadingModule().kodein)
	}
	
	override fun rootModule(activity: Activity) = Kodein {
		extend(kodein)
		import(RootModule(activity).kodein)
	}
	
	override fun checksModule(fragment: Fragment) = Kodein {
		extend(kodein)
		import(ChecksModule(fragment).kodein)
	}
	
	override fun checkModule(fragment: Fragment) = Kodein {
		extend(kodein)
		import(CheckModule(fragment).kodein)
	}
}