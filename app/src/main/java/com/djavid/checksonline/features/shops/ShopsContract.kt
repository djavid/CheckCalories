package com.djavid.checksonline.features.shops

import com.djavid.checksonline.model.entities.PlaceholderDate
import com.djavid.checksonline.model.entities.Receipt

class ShopsContract {

    interface View {
        fun init(presenter: Presenter)
        fun showChecks(checks: List<Receipt>, remove: Boolean)
        fun setToolbarTitle(title: String)
        fun loadingDone()
        fun noMoreToLoad()
        fun removeAllViews()
        fun showProgress(show: Boolean)
    }

    interface Presenter {
        fun init()
        fun onDestroy()
        fun onCheckClicked(receipt: Receipt)
        fun loadMoreChecks()
        fun refresh()
        fun loadChecks(page: Int, show: Boolean)
        fun getPlaceholderDates(checks: List<Receipt>): List<PlaceholderDate>
    }
}