package com.djavid.checksonline.dagger

import android.app.Application
import android.content.Context
import com.djavid.checksonline.utils.SavedPreferences
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class AndroidModule {

    @AppContext
    @Singleton
    @Provides
    fun provideAppContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideSavedPreferences(@AppContext context: Context): SavedPreferences =
            SavedPreferences(context.getSharedPreferences("CheckIt", Context.MODE_PRIVATE))

    @Provides
    @Singleton
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder = cicerone.navigatorHolder

    @Provides
    @Singleton
    fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router

}

@Qualifier
annotation class AppContext