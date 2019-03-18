package com.djavid.checksonline.features.root

import javax.inject.Inject

class NewRootView @Inject constructor(
        @ViewRoot private val viewRoot: ViewRoot
): RootContract.View {

    private lateinit var presenter: RootContract.Presenter

    override fun init(presenter: RootContract.Presenter) {
        this.presenter = presenter
    }
}