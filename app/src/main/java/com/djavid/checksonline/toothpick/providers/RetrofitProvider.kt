package com.djavid.checksonline.toothpick.providers

import com.djavid.checksonline.toothpick.qualifiers.BaseUrl
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit

import javax.inject.Inject
import javax.inject.Provider

class RetrofitProvider @Inject constructor(
        @BaseUrl private val baseUrl: String,
        private val callAdapterFactory: CallAdapter.Factory,
        private val client: OkHttpClient,
        private val converterFactory: Converter.Factory
) : Provider<Retrofit> {

    override fun get(): Retrofit {

        return Retrofit.Builder()
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(converterFactory)
                .baseUrl(baseUrl)
                .client(client)
                .build()
    }
}