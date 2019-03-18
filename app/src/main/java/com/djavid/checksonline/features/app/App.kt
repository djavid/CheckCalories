package com.djavid.checksonline.features.app

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber
import javax.inject.Inject

class App : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector


    override fun onCreate() {
        super.onCreate()
        initTimber()
        JodaTimeAndroid.init(this)
    }

//    fun createAppComponent = DaggerAppComponent
//                        .builder()
//                        .androidAppModule(AndroidAppModule(this))
//                        .build()
//                        .inject(this)

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

}