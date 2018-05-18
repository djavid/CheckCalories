package com.djavid.checksonline.model.networking.apis

import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.model.networking.responses.FlaskResponse
import com.djavid.checksonline.model.networking.bodies.FlaskValues
import com.djavid.checksonline.model.networking.responses.GetChecksResponse
import com.djavid.checksonline.model.networking.responses.SendCheckResponse
import io.reactivex.Single
import retrofit2.http.*

interface BaseApi {

    @POST("receipt")
    fun sendCheck(@Header("Token") token:String,
                  @Body receipt: Receipt) : Single<SendCheckResponse>

    @GET("receipt")
    fun getChecks(@Header("Token") token:String,
                  @Query("page") page: Int) : Single<GetChecksResponse>

    @GET("receipt/{id}")
    fun getCheck(@Header("Token") token:String,
                 @Path("id") id: Long) : Single<Receipt>

    @POST("https://predictcheck.herokuapp.com/predict")
    fun getCategories(@Body values: FlaskValues) : Single<FlaskResponse>

}