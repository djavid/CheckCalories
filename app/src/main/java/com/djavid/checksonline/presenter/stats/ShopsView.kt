package com.djavid.checksonline.presenter.stats

import com.djavid.checksonline.base.BaseView
import com.djavid.checksonline.model.entities.Receipt

interface ShopsView : BaseView {

    fun showChecks(checks: List<Receipt>)
    fun setToolbarTitle(title: String)

}