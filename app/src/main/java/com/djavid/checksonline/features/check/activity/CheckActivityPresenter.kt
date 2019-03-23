package com.djavid.checksonline.features.check.activity

import ru.terrakok.cicerone.Router
import javax.inject.Inject

class CheckActivityPresenter @Inject constructor(
        private val view: CheckActivityContract.View,
        private val router: Router
) : CheckActivityContract.Presenter {

    override fun init(checkId: String) {

    }

}