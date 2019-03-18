package com.djavid.checksonline.features.root

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.Screens
import com.djavid.checksonline.features.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@Deprecated("")
@InjectViewState
class RootPresenter @Inject constructor(router: Router) : BasePresenter<RootView>(router)  {

    fun onQRSelected() {
        router.newRootScreen(Screens.QR_CODE)
    }

    fun onChecksSelected() {
        router.newRootScreen(Screens.HOME)
    }

    fun onStatsSelected() {
        router.newRootScreen(Screens.STATS)
    }
}