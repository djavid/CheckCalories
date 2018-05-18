package com.djavid.checksonline.model.repositories

import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.model.networking.apis.BaseApi
import com.djavid.checksonline.model.networking.responses.FlaskResponse
import com.djavid.checksonline.model.networking.bodies.FlaskValues
import com.djavid.checksonline.model.networking.responses.GetChecksResponse
import com.djavid.checksonline.model.networking.responses.SendCheckResponse
import io.reactivex.Single
import javax.inject.Inject

class BaseRepository @Inject constructor(
        private val baseApi: BaseApi
) {

    val token = "fxvsdzxcvxzcv"

    fun sendCheck(receipt: Receipt) : Single<SendCheckResponse> {
        return baseApi.sendCheck(token, receipt)
    }

    fun getChecks(page: Int) : Single<GetChecksResponse> {
        return baseApi.getChecks(token, page)
    }

    fun getCheck(id: Long) : Single<Receipt> {
        return baseApi.getCheck(token, id)
    }

    fun getCategories(values: FlaskValues): Single<FlaskResponse> {
        return baseApi.getCategories(values)
    }

}