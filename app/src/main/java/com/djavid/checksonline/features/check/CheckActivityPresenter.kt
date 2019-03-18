package com.djavid.checksonline.features.check

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.features.base.BasePresenter
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