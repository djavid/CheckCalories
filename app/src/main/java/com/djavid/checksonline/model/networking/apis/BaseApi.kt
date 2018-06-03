package com.djavid.checksonline.model.networking.apis

import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.model.networking.bodies.FnsValues
import com.djavid.checksonline.model.networking.responses.*
import io.reactivex.Single
import retrofit2.http.*

interface BaseApi {

//    @POST("receipt")
//    fun sendCheck(@Header("Token") token: String,
//                  @Body receipt: Receipt): Single<SendCheckResponse>

//    @POST("https://predictcheck.herokuapp.com/predict")
//    fun getCategories(@Body values: FlaskValues): Single<FlaskResponse>

    //check

    @GET("receipt")
    fun getChecks(@Header("Token") token: String,
                  @Query("page") page: Int): Single<GetChecksResponse>

    @GET("receipt/{id}")
    fun getCheck(@Header("Token") token: String,
                 @Path("id") id: Long): Single<Receipt>

    @POST("receipt")
    fun sendCheck(@Header("Token") token: String,
                  @Body fnsValues: FnsValues): Single<SendCheckResponse>

    //token

    @POST("token")
    fun sendToken(@Header("Token") token: String): Single<SendTokenResponse>

    //stats

    @GET("stats/intervals")
    fun getIntervals(@Header("Token") token: String,
                     @Query("interval") interval: String) : Single<GetIntervalsResponse>

    @GET("stats")
    fun getStats(@Header("Token") token: String,
                 @Query("start") start: Long,
                 @Query("end") end: Long) : Single<StatPercentResponse>

    @GET("stats/total")
    fun getTotalSum(@Header("Token") token: String,
                    @Query("type") type: String) : Single<GetTotalSumResponse>

}