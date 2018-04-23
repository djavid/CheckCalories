package com.djavid.checksonline.toothpick.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.djavid.checksonline.toothpick.providers.SavedPreferencesProvider
import com.djavid.checksonline.utils.SavedPreferences
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module
import toothpick.smoothie.provider.SharedPreferencesProvider

class AndroidModule(context: Context, application: Application) : Module() {

    init {
        bind(Context::class.java).toInstance(context)

        bind(SharedPreferences::class.java)
                .toProvider(SharedPreferencesProvider(application)::class.java)

        bind(SavedPreferences::class.java)
                .toProvider(SavedPreferencesProvider::class.java)
                .providesSingletonInScope()

        val cicerone = Cicerone.create()
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)
        bind(Router::class.java).toInstance(cicerone.router)
    }
}