package com.djavid.checksonline.features.root

import android.view.View
import javax.inject.Inject

class RootView @Inject constructor(
        @ViewRoot private val viewRoot: View
) : RootContract.View {

    private lateinit var presenter: RootContract.Presenter

    override fun init(presenter: RootContract.Presenter) {
        this.presenter = presenter
    }

}