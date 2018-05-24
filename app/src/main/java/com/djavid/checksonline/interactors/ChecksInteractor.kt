package com.djavid.checksonline.interactors

import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.model.networking.responses.GetChecksResponse
import com.djavid.checksonline.model.repositories.BaseRepository
import com.djavid.checksonline.model.threading.SchedulersProvider
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.Single
import javax.inject.Inject

class ChecksInteractor @Inject constructor(
        private val baseRepository: BaseRepository,
        private val schedulers: SchedulersProvider
) {

    private val retryTimes: Long = 2

    fun getChecks(page: Int): Single<GetChecksResponse> =
        baseRepository.getChecks(page, FirebaseInstanceId.getInstance().token ?: "")
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                //.retry(retryTimes)

    fun getCheck(id: Long): Single<Receipt> =
            baseRepository.getCheck(id, FirebaseInstanceId.getInstance().token ?: "")
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .retry(retryTimes)

}