package com.djavid.checksonline.interactors

import com.djavid.checksonline.model.networking.responses.GetHabitsResponse
import com.djavid.checksonline.model.networking.responses.GetIntervalsResponse
import com.djavid.checksonline.model.networking.responses.GetTotalSumResponse
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

    private val retryTimes: Long = 3

    fun getChecks(start: Long, end: Long): Single<StatPercentResponse> =
            baseRepository
                    .getStats(FirebaseInstanceId.getInstance().token ?: "", start, end)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .retry(retryTimes)

    //interval = int of days, 'month', 'week', 'day'
    fun getIntervals(interval: String) : Single<GetIntervalsResponse> =
            baseRepository
                    .getIntervals(FirebaseInstanceId.getInstance().token ?: "", interval)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .retry(retryTimes)

    fun getTotalSum(type: String) : Single<GetTotalSumResponse> =
            baseRepository
                    .getTotalSum(FirebaseInstanceId.getInstance().token ?: "", type)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .retry(retryTimes)

    fun getHabits() : Single<GetHabitsResponse> =
            baseRepository.getHabits(FirebaseInstanceId.getInstance().token ?: "")
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .retry(retryTimes)

}