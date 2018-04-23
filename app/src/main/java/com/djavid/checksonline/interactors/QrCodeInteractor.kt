package com.djavid.checksonline.interactors

import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.model.repositories.BaseRepository
import com.djavid.checksonline.model.repositories.FnsRepository
import com.djavid.checksonline.model.threading.SchedulersProvider
import io.reactivex.Single
import okhttp3.ResponseBody
import javax.inject.Inject

class QrCodeInteractor @Inject constructor(
        private val fnsRepository: FnsRepository,
        private val baseRepository: BaseRepository,
        private val schedulers: SchedulersProvider
) {

    fun getCheck(fiscalDriveNumber: String, fiscalDocumentNumber: String,
                 fiscalSign: String, sendToEmail: Boolean): Single<ResponseBody> =
            fnsRepository.getCheck(fiscalDriveNumber, fiscalDocumentNumber, fiscalSign, sendToEmail)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())

    fun sendCheck(receipt: Receipt) : Single<Receipt> =
            baseRepository.sendCheck(receipt)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())

}