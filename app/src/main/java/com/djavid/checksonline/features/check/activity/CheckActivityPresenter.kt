package com.djavid.checksonline.features.check.activity

import javax.inject.Inject

class CheckActivityPresenter @Inject constructor(
        private val view: CheckActivityContract.View
) : CheckActivityContract.Presenter {

    override fun init(checkId: String) {

    }

}