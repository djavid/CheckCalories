package com.djavid.checksonline.features.check_new

import android.view.ViewGroup
import com.djavid.checksonline.features.root.ViewRoot
import javax.inject.Inject

class NewCheckView @Inject constructor(
        @ViewRoot val viewRoot: ViewGroup
) : NewCheckContract.View {

    private lateinit var presenter: NewCheckContract.Presenter

    override fun init(presenter: NewCheckContract.Presenter) {
        this.presenter = presenter
    }
}