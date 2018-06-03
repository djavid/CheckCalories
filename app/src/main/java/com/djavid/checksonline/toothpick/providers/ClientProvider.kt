package com.djavid.checksonline.toothpick.providers

import android.content.Context
//import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Provider

class ClientProvider @Inject constructor(
    private val context: Context
) : Provider<OkHttpClient> {

    override fun get(): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .addInterceptor(logging)
//                .addInterceptor(ChuckInterceptor(context))
                .build()
    }
}