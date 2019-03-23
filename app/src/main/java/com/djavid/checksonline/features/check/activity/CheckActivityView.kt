package com.djavid.checksonline.features.check.activity

import android.view.View
import com.djavid.checksonline.features.root.ViewRoot
import javax.inject.Inject

class CheckActivityView @Inject constructor(
        @ViewRoot private val viewRoot: View
) : CheckActivityContract.View {

    private lateinit var presenter: CheckActivityContract.Presenter

    override fun init(presenter: CheckActivityContract.Presenter) {
        this.presenter = presenter
    }
}