package com.djavid.checksonline

import android.app.Application
import com.djavid.checksonline.toothpick.Scopes
import com.djavid.checksonline.toothpick.modules.AndroidModule
import com.djavid.checksonline.toothpick.modules.NetworkingModule
import com.djavid.checksonline.toothpick.modules.ThreadingModule
import com.djavid.checksonline.utils.Injection
import net.danlew.android.joda.JodaTimeAndroid
import toothpick.Toothpick
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initToothpick()
        initCalligraphy()
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

    private fun initCalligraphy() {
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .build())
    }

}