package com.djavid.checksonline.dagger

import com.djavid.checksonline.features.app.BaseUrl
import com.djavid.checksonline.model.networking.apis.BaseApi
import com.djavid.checksonline.model.networking.apis.FnsApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
    }

    @Provides
    @Singleton
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
            .setDateFormat("yyyy-mm-dd HH:mm:ss")
            .create()

    @Provides
    @Singleton
    fun provideGsonConverter(gson: Gson): Converter.Factory = GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideRetrofit(
            client: OkHttpClient,
            @BaseUrl baseUrl: String,
            converterFactory: Converter.Factory,
            callAdapterFactory: CallAdapter.Factory
    ): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(converterFactory)
                .baseUrl(baseUrl)
                .client(client)
                .build()
    }

    @Provides
    @Singleton
    fun provideBaseApi(retrofit: Retrofit): BaseApi = retrofit.create(BaseApi::class.java)

    @Provides
    @Singleton
    fun provideFnsApi(retrofit: Retrofit): FnsApi = retrofit.create(FnsApi::class.java)

}