package com.djavid.checksonline.model.repositories

import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.model.networking.apis.BaseApi
import com.djavid.checksonline.model.networking.bodies.FnsValues
import com.djavid.checksonline.model.networking.responses.BaseStringResponse
import com.djavid.checksonline.model.networking.responses.GetChecksResponse
import com.djavid.checksonline.model.networking.responses.GetHabitsResponse
import com.djavid.checksonline.model.networking.responses.GetIntervalsResponse
import com.djavid.checksonline.model.networking.responses.GetItemsResponse
import com.djavid.checksonline.model.networking.responses.GetTotalSumResponse
import com.djavid.checksonline.model.networking.responses.SendCheckResponse
import com.djavid.checksonline.model.networking.responses.SendTokenResponse
import com.djavid.checksonline.model.networking.responses.StatPercentResponse
import io.reactivex.Single

class BaseRepository constructor(
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

    fun getIntervals(token: String, interval: String): Single<GetIntervalsResponse> {
        return baseApi.getIntervals(token, interval)
    }

    fun getTotalSum(token: String, type: String): Single<GetTotalSumResponse> {
        return baseApi.getTotalSum(token, type)
    }


    fun getChecksByShop(token: String, shop: String, start: Long, end: Long, page: Int)
            : Single<GetChecksResponse> {
        return baseApi.getChecksByShop(token, shop, start, end, page)
    }

    fun getItemsByCategory(token: String, category: String, start: Long, end: Long, page: Int)
            : Single<GetItemsResponse> {
        return baseApi.getItemsByCategory(token, category, start, end, page)
    }

    fun getHabits(token: String) : Single<GetHabitsResponse> {
        return baseApi.getHabits(token)
    }

    fun removeCheck(token: String, id: Long) : Single<BaseStringResponse> {
        return baseApi.removeCheck(token, id)
    }

}