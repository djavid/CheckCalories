package com.djavid.checksonline.model.repositories

import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.model.networking.apis.BaseApi
import com.djavid.checksonline.model.networking.bodies.FnsValues
import com.djavid.checksonline.model.networking.responses.GetChecksResponse
import com.djavid.checksonline.model.networking.responses.SendCheckResponse
import com.djavid.checksonline.model.networking.responses.SendTokenResponse
import com.djavid.checksonline.model.networking.responses.StatPercentResponse
import io.reactivex.Single
import javax.inject.Inject

class BaseRepository @Inject constructor(
        private val baseApi: BaseApi
) {

//    fun sendCheck(receipt: Receipt, token: String): Single<SendCheckResponse> {
//        return baseApi.sendCheck(token, receipt)
//    }

//    fun getCategories(values: FlaskValues): Single<FlaskResponse> {
//        return baseApi.getCategories(values)
//    }

    fun sendCheck(fnsValues: FnsValues, token: String): Single<SendCheckResponse> {
        return baseApi.sendCheck(token, fnsValues)
    }

    fun getChecks(page: Int, token: String): Single<GetChecksResponse> {
        return baseApi.getChecks(token, page)
    }

    fun getCheck(id: Long, token: String): Single<Receipt> {
        return baseApi.getCheck(token, id)
    }

    fun sendToken(token: String): Single<SendTokenResponse> {
        return baseApi.sendToken(token)
    }

    fun getStats(token: String, start: Long, end: Long): Single<StatPercentResponse> {
        return baseApi.getStats(token, start, end)
    }

}