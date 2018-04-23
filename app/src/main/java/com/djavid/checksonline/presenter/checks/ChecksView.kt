package com.djavid.checksonline.presenter.checks

import com.djavid.checksonline.base.BaseView
import com.djavid.checksonline.model.entities.Receipt

interface ChecksView : BaseView {

    fun setChecks(checks: List<Receipt>)

    fun showChecks(checks: List<Receipt>)
    fun showChecksProgress(show: Boolean, isEmpty: Boolean)
    fun showChecksError(show: Boolean, message: String)
    fun loadingDone()

}