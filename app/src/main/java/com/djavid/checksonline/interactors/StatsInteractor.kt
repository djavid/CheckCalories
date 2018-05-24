package com.djavid.checksonline.interactors

import com.djavid.checksonline.model.networking.responses.StatPercentResponse
import com.djavid.checksonline.model.repositories.BaseRepository
import com.djavid.checksonline.model.threading.SchedulersProvider
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.Single
import javax.inject.Inject

class StatsInteractor @Inject constructor(
        private val baseRepository: BaseRepository,
        private val schedulers: SchedulersProvider
) {

    fun getChecks(start: Long, end: Long): Single<StatPercentResponse> =
            baseRepository
                    .getStats(FirebaseInstanceId.getInstance().token ?: "", start, end)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .retry(3)

}