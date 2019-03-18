package com.djavid.checksonline.features.stats

import com.djavid.checksonline.features.base.BaseView
import com.djavid.checksonline.model.entities.Receipt

interface ShopsView : BaseView {

    fun showChecks(checks: List<Receipt>, remove: Boolean)
    fun setToolbarTitle(title: String)

    fun loadingDone()
    fun noMoreToLoad()
    fun removeAllViews()

}