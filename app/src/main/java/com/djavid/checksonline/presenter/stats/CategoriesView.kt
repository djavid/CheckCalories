package com.djavid.checksonline.presenter.stats

import com.djavid.checksonline.base.BaseView
import com.djavid.checksonline.model.entities.StatItem

interface CategoriesView : BaseView {

    fun showItems(items: List<StatItem>, remove: Boolean)
    fun setToolbarTitle(title: String)

    fun loadingDone()
    fun noMoreToLoad()
    fun removeAllViews()

}