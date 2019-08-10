package com.djavid.checksonline.view.categories

import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.model.entities.PlaceholderDate
import com.djavid.checksonline.model.entities.StatItem

class CategoriesContract {

    interface View {
        fun init(presenter: Presenter)
        fun showItems(items: List<StatItem>, remove: Boolean)
        fun setToolbarTitle(title: String)
        fun loadingDone()
        fun noMoreToLoad()
        fun removeAllViews()
        fun showProgress(show: Boolean)
    }

    interface Presenter {
        fun init(interval: DateInterval)
        fun loadMoreChecks()
        fun refresh()
        fun getPlaceholderDates(checks: List<StatItem>): List<PlaceholderDate>
        fun onDestroy()
    }
}