package com.djavid.checksonline.interactors

import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.model.networking.responses.FlaskResponse
import com.djavid.checksonline.model.networking.bodies.FlaskValues
import com.djavid.checksonline.model.repositories.BaseRepository
import com.djavid.checksonline.model.threading.SchedulersProvider
import io.reactivex.Single
import javax.inject.Inject

class ChecksInteractor @Inject constructor(
        private val baseRepository: BaseRepository,
        private val schedulers: SchedulersProvider
) {

    private val retryTimes: Long = 2

    fun getChecks(page: Int): Single<List<Receipt>> =
            baseRepository.getChecks(page)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .retry(retryTimes)

    fun getCheck(id: Long): Single<Receipt> =
            baseRepository.getCheck(id)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .retry(retryTimes)

    fun getCategories(values: FlaskValues): Single<FlaskResponse> =
            baseRepository.getCategories(values)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .retry(retryTimes)

}