package com.djavid.checksonline.toothpick.modules

import android.app.Application
import android.content.Context
import com.djavid.checksonline.utils.SavedPreferences
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module

class AndroidModule(context: Context, application: Application) : Module() {

    init {
        bind(Context::class.java).toInstance(context)

//        bind(SharedPreferences::class.java) to
//                context.getSharedPreferences("CheckIt", Context.MODE_PRIVATE)

        bind(SavedPreferences::class.java)
                .toInstance(SavedPreferences(
                        context.getSharedPreferences("CheckIt", Context.MODE_PRIVATE)
                ))

        val cicerone = Cicerone.create()
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)
        bind(Router::class.java).toInstance(cicerone.router)
    }
}