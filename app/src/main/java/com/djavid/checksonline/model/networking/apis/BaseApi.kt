package com.djavid.checksonline.model.networking.apis

import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.model.networking.responses.FlaskResponse
import com.djavid.checksonline.model.networking.bodies.FlaskValues
import io.reactivex.Single
import retrofit2.http.*

interface BaseApi {

    @POST("receipt")
    fun sendCheck(@Body receipt: Receipt) : Single<Receipt>

    @GET("receipt")
    fun getChecks(@Query("page") page: Int) : Single<List<Receipt>>

    @GET("receipt/{id}")
    fun getCheck(@Path("id") id: Long) : Single<Receipt>

    @POST("https://predictcheck.herokuapp.com/predict")
    fun getCategories(@Body values: FlaskValues) : Single<FlaskResponse>

}