package com.djavid.checksonline.features.checks

import com.djavid.checksonline.features.base.BaseView
import com.djavid.checksonline.model.entities.Dates
import com.djavid.checksonline.model.entities.Receipt

interface ChecksView : BaseView {

    fun showChecks(checks: List<Receipt>)
    fun showChecksProgress(show: Boolean, isEmpty: Boolean)
    fun showChecksError(show: Boolean)
    fun loadingDone()
    fun noMoreToLoad()
    fun removeAllViews()
    fun setLoadMoreResolver()
    fun showEmptyView(show: Boolean)

    fun setToolbarSum(totalSum: Double)
    fun setBtnPeriodText(interval: Dates)

}