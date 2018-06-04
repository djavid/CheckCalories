package com.djavid.checksonline.presenter.stats

import com.djavid.checksonline.base.BaseView
import com.djavid.checksonline.model.entities.Receipt

interface ShopsView : BaseView {

    fun showChecks(checks: List<Receipt>, remove: Boolean)
    fun setToolbarTitle(title: String)

    fun loadingDone()
    fun noMoreToLoad()
    fun removeAllViews()

}