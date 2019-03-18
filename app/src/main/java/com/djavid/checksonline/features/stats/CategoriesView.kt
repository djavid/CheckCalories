package com.djavid.checksonline.features.stats

import com.djavid.checksonline.features.base.BaseView
import com.djavid.checksonline.model.entities.StatItem

interface CategoriesView : BaseView {

    fun showItems(items: List<StatItem>, remove: Boolean)
    fun setToolbarTitle(title: String)

    fun loadingDone()
    fun noMoreToLoad()
    fun removeAllViews()

}