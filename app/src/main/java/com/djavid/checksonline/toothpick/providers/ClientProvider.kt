package com.djavid.checksonline.toothpick.providers

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Provider

class ClientProvider @Inject constructor(

) : Provider<OkHttpClient> {

    override fun get(): OkHttpClient {

        val logging = HttpLoggingInterceptor();
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
    }
}