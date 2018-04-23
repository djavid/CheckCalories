package com.djavid.checksonline.toothpick.providers

import com.djavid.checksonline.model.networking.apis.FnsApi
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Provider

class FnsApiProvider @Inject constructor(
        private val retrofit: Retrofit
) : Provider<FnsApi> {
    override fun get(): FnsApi = retrofit.create(FnsApi::class.java)
}