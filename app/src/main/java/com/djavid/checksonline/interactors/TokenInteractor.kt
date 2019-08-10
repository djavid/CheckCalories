package com.djavid.checksonline.interactors

import com.djavid.checksonline.model.networking.responses.SendTokenResponse
import com.djavid.checksonline.model.repositories.BaseRepository
import com.djavid.checksonline.model.threading.SchedulersProvider
import io.reactivex.Single

class TokenInteractor constructor(
        private val baseRepository: BaseRepository,
        private val schedulers: SchedulersProvider
)  {

    fun sendToken(token: String) : Single<SendTokenResponse> {
        return baseRepository.sendToken(token)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .retry(3)
    }

}