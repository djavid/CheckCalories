package com.djavid.checksonline.model.networking.apis

import com.djavid.checksonline.BuildConfig
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FnsApi {

    @GET(BuildConfig.FNS_URL + "v1/inns/*/kkts/*/fss/{fss}/tickets/{tickets}")
    fun getCheck(
            @Path("fss") fiscalDriveNumber: String,
            @Path("tickets") fiscalDocumentNumber: String,

            @Query("fiscalSign") fiscalSign: String,
            @Query("sendToEmail") sendToEmail: String,

            @Header("Authorization") authKey: String,
            @Header("Device-Id") deviceId: String,
            @Header("Device-OS") deviceOS: String,
            @Header("Version") version: String,
            @Header("ClientVersion") clientVersion: String,
            @Header("Host") host: String,
            @Header("Connection") connection: String,
            @Header("Accept-Encoding") acceptEncoding: String,
            @Header("User-Agent") userAgent: String
    ): Single<ResponseBody>

}