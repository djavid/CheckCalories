package com.djavid.checksonline.utils

//@Deprecated("")
//class FirebaseManager : FirebaseInstanceIdService() {
//
//    private lateinit var tokenInteractor: TokenInteractor
//
//    override fun onCreate() {
//
//    }
//
//    @SuppressLint("CheckResult")
//    override fun onTokenRefresh() {
//        val refreshedToken = FirebaseInstanceId.getInstance().token
//        refreshedToken ?: return
//
//        Timber.i("Token: %s", refreshedToken)
//
//        tokenInteractor.sendToken(refreshedToken)
//                .subscribe({
//                    Timber.i(it.toString())
//                }, {
//                    Timber.e(it)
//                })
//    }
//}