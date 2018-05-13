package com.djavid.checksonline.model.repositories

import com.djavid.checksonline.model.networking.apis.FnsApi
import com.djavid.checksonline.model.networking.responses.CheckResponseFns
import com.djavid.checksonline.utils.getAuthToken
import io.reactivex.Single
import okhttp3.ResponseBody
import javax.inject.Inject

class FnsRepository @Inject constructor(
        private val fnsApi: FnsApi
) {

    fun getCheck(fiscalDriveNumber: String, fiscalDocumentNumber: String, fiscalSign: String,
                 sendToEmail: Boolean): Single<CheckResponseFns> {

        val os = "Android " + android.os.Build.VERSION.RELEASE
        val email = if (sendToEmail) "yes" else "no"

        return fnsApi.getCheck(fiscalDriveNumber, fiscalDocumentNumber, fiscalSign, email,
                getAuthToken(), "", os, "2", "1.4.4.1",
                "proverkacheka.nalog.ru:9999", "Keep-Alive", "gzip",
                "okhttp/3.0.1")
    }

}