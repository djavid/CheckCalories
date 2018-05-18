package com.djavid.checksonline.presenter.check

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.Screens
import com.djavid.checksonline.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class CheckActivityPresenter @Inject constructor(
        router: Router
) : BasePresenter<CheckActivityView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}