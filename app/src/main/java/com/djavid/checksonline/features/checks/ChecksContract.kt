package com.djavid.checksonline.features.checks

import com.djavid.checksonline.model.entities.Dates
import com.djavid.checksonline.model.entities.PlaceholderDate
import com.djavid.checksonline.model.entities.Receipt

class ChecksContract {

    interface View {
        fun init(presenter: Presenter)
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
        fun openPanel()
    }

    interface Presenter {
        fun init()
        fun onDateIntervalChosen(interval: Dates)
        fun removeCheck(id: Long)
        fun refresh()
        fun onFabClicked()
        fun onCheckClicked(receipt: Receipt)
        fun loadMoreChecks()
        fun getPlaceholderDates(checks: List<Receipt>): List<PlaceholderDate>
        fun onDestroy()
    }
}