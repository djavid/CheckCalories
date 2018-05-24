package com.djavid.checksonline

import android.app.Application
import com.djavid.checksonline.toothpick.modules.AndroidModule
import com.djavid.checksonline.toothpick.modules.NetworkingModule
import com.djavid.checksonline.toothpick.modules.ThreadingModule
import com.djavid.checksonline.utils.Injection
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber
import toothpick.Toothpick

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initToothpick()
        //initPicasso()
        initTimber()
        JodaTimeAndroid.init(this);
    }

    private fun initToothpick() {
        Injection().initialize()
        Toothpick.openScope(this).installModules( //Scopes.APPLICATION
                AndroidModule(this, this),
                NetworkingModule(BuildConfig.BASE_URL),
                ThreadingModule()
        )
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initPicasso() {
        val builder = Picasso.Builder(this)
        builder.downloader(OkHttp3Downloader(this, Integer.MAX_VALUE.toLong()))
        val built = builder.build()
        Picasso.setSingletonInstance(built)
    }

}