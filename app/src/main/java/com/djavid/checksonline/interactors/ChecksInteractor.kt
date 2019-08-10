package com.djavid.checksonline.interactors

import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.model.networking.responses.BaseStringResponse
import com.djavid.checksonline.model.networking.responses.GetChecksResponse
import com.djavid.checksonline.model.repositories.BaseRepository
import com.djavid.checksonline.model.threading.SchedulersProvider
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.Single

class ChecksInteractor constructor(
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

    fun getChecksByShop(shop: String, start: Long, end: Long, page: Int) =
            baseRepository
                    .getChecksByShop(FirebaseInstanceId.getInstance().token ?: "",
                            shop, start, end, page)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .retry(retryTimes)

    fun getItemsByCategory(category: String, start: Long, end: Long, page: Int) =
            baseRepository
                    .getItemsByCategory(FirebaseInstanceId.getInstance().token ?: "",
                            category, start, end, page)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .retry(retryTimes)

    fun removeCheck(id: Long) : Single<BaseStringResponse> =
            baseRepository
                    .removeCheck(FirebaseInstanceId.getInstance().token ?: "", id)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .retry(retryTimes)
}