package com.djavid.checksonline.toothpick.providers

import com.google.gson.Gson
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Provider

class ConverterProvider @Inject constructor(private val gson: Gson) : Provider<Converter.Factory> {
    override fun get(): Converter.Factory = GsonConverterFactory.create(gson)
}