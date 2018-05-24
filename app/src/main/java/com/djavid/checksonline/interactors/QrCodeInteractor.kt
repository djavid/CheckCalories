package com.djavid.checksonline.interactors

import com.djavid.checksonline.model.networking.bodies.FnsValues
import com.djavid.checksonline.model.networking.responses.CheckResponseFns
import com.djavid.checksonline.model.networking.responses.SendCheckResponse
import com.djavid.checksonline.model.repositories.BaseRepository
import com.djavid.checksonline.model.repositories.FnsRepository
import com.djavid.checksonline.model.threading.SchedulersProvider
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.Single
import javax.inject.Inject

class QrCodeInteractor @Inject constructor(
        private val fnsRepository: FnsRepository,
        private val baseRepository: BaseRepository,
        private val schedulers: SchedulersProvider
) {

    fun getCheck(fiscalDriveNumber: String, fiscalDocumentNumber: String,
                 fiscalSign: String, sendToEmail: Boolean): Single<CheckResponseFns> =
            fnsRepository.getCheck(fiscalDriveNumber, fiscalDocumentNumber, fiscalSign, sendToEmail)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .retry(2)

//    fun sendCheck(receipt: Receipt) : Single<SendCheckResponse> =
//            baseRepository.sendCheck(receipt, FirebaseInstanceId.getInstance().token ?: "")
//                    .subscribeOn(schedulers.io())
//                    .observeOn(schedulers.ui())
//                    .retry(2)

    fun sendCheck(fnsValues: FnsValues) : Single<SendCheckResponse> =
            baseRepository.sendCheck(fnsValues, FirebaseInstanceId.getInstance().token ?: "")
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .retry(2)

}