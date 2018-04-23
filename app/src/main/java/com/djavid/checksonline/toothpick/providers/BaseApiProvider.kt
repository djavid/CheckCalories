package com.djavid.checksonline.toothpick.providers

import com.djavid.checksonline.model.networking.apis.BaseApi
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Provider

class BaseApiProvider @Inject constructor(
        private val retrofit: Retrofit
) : Provider<BaseApi> {
    override fun get(): BaseApi = retrofit.create(BaseApi::class.java)
}