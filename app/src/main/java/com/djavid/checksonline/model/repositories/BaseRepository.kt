package com.djavid.checksonline.model.repositories

import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.model.networking.apis.BaseApi
import com.djavid.checksonline.model.networking.responses.FlaskResponse
import com.djavid.checksonline.model.networking.bodies.FlaskValues
import io.reactivex.Single
import javax.inject.Inject

class BaseRepository @Inject constructor(
        private val baseApi: BaseApi
) {

    fun sendCheck(receipt: Receipt) : Single<Receipt> {
        return baseApi.sendCheck(receipt)
    }

    fun getChecks(page: Int) : Single<List<Receipt>> {
        return baseApi.getChecks(page)
    }

    fun getCheck(id: Long) : Single<Receipt> {
        return baseApi.getCheck(id)
    }

    fun getCategories(values: FlaskValues): Single<FlaskResponse> {
        return baseApi.getCategories(values)
    }

}