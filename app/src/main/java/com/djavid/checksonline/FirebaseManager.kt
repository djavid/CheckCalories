package com.djavid.checksonline

import com.djavid.checksonline.interactors.TokenInteractor
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import timber.log.Timber
import toothpick.Toothpick

class FirebaseManager : FirebaseInstanceIdService() {

    private lateinit var tokenInteractor: TokenInteractor

    override fun onCreate() {
        tokenInteractor = Toothpick.openScope(application).getInstance(TokenInteractor::class.java)
    }

    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token
        refreshedToken ?: return

        Timber.i("Token: %s", refreshedToken)

        tokenInteractor.sendToken(refreshedToken)
                .subscribe({
                    Timber.i(it.toString())
                }, {
                    Timber.e(it)
                })
    }
}