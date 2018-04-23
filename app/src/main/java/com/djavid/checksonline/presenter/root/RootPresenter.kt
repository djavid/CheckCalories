package com.djavid.checksonline.presenter.root

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.Screens
import com.djavid.checksonline.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class RootPresenter @Inject constructor(router: Router) : BasePresenter<RootView>(router)  {

    fun onQRSelected() {
        router.newRootScreen(Screens.QR_CODE)
    }
}