package com.djavid.checksonline.toothpick.modules

import com.djavid.checksonline.toothpick.providers.*
import com.djavid.checksonline.toothpick.qualifiers.BaseUrl
import com.djavid.checksonline.model.networking.apis.*

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import toothpick.config.Module

class NetworkingModule(baseUrl: String) : Module() {

    init {
        this.bind(String::class.java).withName(BaseUrl::class.java).toInstance(baseUrl)

        this.bind(OkHttpClient::class.java)
                .toProvider(ClientProvider::class.java)
                .providesSingletonInScope()

        this.bind(CallAdapter.Factory::class.java)
                .toProvider(CallAdapterProvider::class.java)
                .providesSingletonInScope()
        this.bind(Gson::class.java)
                .toProvider(GsonProvider::class.java)
                .providesSingletonInScope()
        this.bind(Converter.Factory::class.java)
                .toProvider(ConverterProvider::class.java)
                .providesSingletonInScope()

        this.bind(Retrofit::class.java)
                .toProvider(RetrofitProvider::class.java)
                .providesSingletonInScope()

        // APIs
        this.bind(BaseApi::class.java)
                .toProvider(BaseApiProvider::class.java)
                .providesSingletonInScope()

        this.bind(FnsApi::class.java)
                .toProvider(FnsApiProvider::class.java)
                .providesSingletonInScope()

    }

}