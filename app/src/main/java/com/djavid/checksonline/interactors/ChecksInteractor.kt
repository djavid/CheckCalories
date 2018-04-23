package com.djavid.checksonline.interactors

import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.model.repositories.BaseRepository
import com.djavid.checksonline.model.threading.SchedulersProvider
import io.reactivex.Single
import javax.inject.Inject

class ChecksInteractor @Inject constructor(
        private val baseRepository: BaseRepository,
        private val schedulers: SchedulersProvider
) {

    fun getChecks(page: Int) : Single<List<Receipt>> =
            baseRepository.getChecks(page)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .retry(2)

    fun getCheck(id: Long) : Single<Receipt> =
            baseRepository.getCheck(id)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .retry(2)

}