package com.djavid.checksonline.features.check_new

import javax.inject.Inject

class NewCheckPresenter @Inject constructor(
        private val view: NewCheckContract.View
) : NewCheckContract.Presenter {

    override fun init() {
        view.init(this)
    }

}