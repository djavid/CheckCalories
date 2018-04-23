package com.djavid.checksonline.toothpick.providers

import retrofit2.CallAdapter
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Inject
import javax.inject.Provider

class CallAdapterProvider @Inject constructor() : Provider<CallAdapter.Factory> {
    override fun get(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()
}