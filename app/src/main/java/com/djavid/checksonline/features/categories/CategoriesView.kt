package com.djavid.checksonline.features.categories

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.features.checks.DateItem
import com.djavid.checksonline.features.common.EmptyViewHolder
import com.djavid.checksonline.features.common.LoadMoreView
import com.djavid.checksonline.features.root.ViewRoot
import com.djavid.checksonline.model.entities.StatItem
import com.djavid.checksonline.utils.show
import kotlinx.android.synthetic.main.fragment_categories.view.*
import kotlinx.android.synthetic.main.layout_error_action.view.*
import kotlinx.android.synthetic.main.layout_progress.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import javax.inject.Inject

class CategoriesView @Inject constructor(
        @ViewRoot private val view: View
) : CategoriesContract.View {

    private var emptyViewHolder: EmptyViewHolder? = null
    private lateinit var presenter: CategoriesContract.Presenter


    override fun init(presenter: CategoriesContract.Presenter) {
        this.presenter = presenter

        view.categories_placeholder.builder
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(LinearLayoutManager(view.context))
        setLoadMoreResolver()

        emptyViewHolder = EmptyViewHolder(
                view.context.getString(R.string.refresh),
                view.context.getString(R.string.load_error),
                view.needPermissionLayout, { presenter.refresh() })

        view.btn_back.setOnClickListener {
            //TODO presenter.onBackPressed()
        }
    }

    override fun showProgress(show: Boolean) {
        view.progressLayout.show(show)
    }

    private fun setLoadMoreResolver() {
        view.categories_placeholder.setLoadMoreResolver(LoadMoreView(object : LoadMoreView.Callback {
            override fun onShowMore() {
                presenter.loadMoreChecks()
            }
        }))
    }

    override fun removeAllViews() {
        view.categories_placeholder.removeAllViews()
    }

    override fun loadingDone() {
        view.categories_placeholder.loadingDone()
    }

    override fun noMoreToLoad() {
        view.categories_placeholder.loadingDone()
        view.categories_placeholder.noMoreToLoad()
    }

    override fun showItems(items: List<StatItem>, remove: Boolean) {
        if (remove) view.categories_placeholder.removeAllViews()
        loadingDone()

        val dates = presenter.getPlaceholderDates(items)

        view.categories_placeholder.post {
            items.forEachIndexed { index, item ->
                val dateItem = dates.find { it.after == index }

                if (dateItem != null) {
                    view.categories_placeholder.addView(DateItem(view.context, dateItem.dateTime))
                }

                view.categories_placeholder.addView(
                        GoodStatItem(view.context, item)
                )
            }
        }
    }

    override fun setToolbarTitle(title: String) {
        view.toolbar_title.text = title
    }
}