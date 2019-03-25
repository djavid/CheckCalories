package com.djavid.checksonline.dagger

import android.app.Application
import com.djavid.checksonline.BuildConfig
import dagger.Module
import dagger.Provides
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import javax.inject.Qualifier
import javax.inject.Singleton

@Module(includes = [AndroidModule::class, NetworkModule::class, ThreadingModule::class])
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication() = application

    @Provides
    @BaseUrl
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideDecimalFormat(): DecimalFormat {
        val symbols = DecimalFormatSymbols.getInstance()
        symbols.groupingSeparator = ' '

        return DecimalFormat("###,###", symbols)
    }

}

@Qualifier
annotation class BaseUrl