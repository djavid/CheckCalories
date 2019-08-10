package com.djavid.checksonline.view.shops

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.djavid.checksonline.R
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.utils.show
import com.djavid.checksonline.view.check.CheckItem
import com.djavid.checksonline.view.checks.DateItem
import com.djavid.checksonline.view.common.EmptyViewHolder
import com.djavid.checksonline.view.common.LoadMoreView
import kotlinx.android.synthetic.main.fragment_shops.view.*
import kotlinx.android.synthetic.main.layout_error_action.view.*
import kotlinx.android.synthetic.main.layout_progress.view.*
import kotlinx.android.synthetic.main.toolbar.view.*

class ShopsView constructor(
		private val view: View
) : ShopsContract.View {

    private lateinit var presenter: ShopsContract.Presenter
    private var emptyViewHolder: EmptyViewHolder? = null


    override fun init(presenter: ShopsContract.Presenter) {
        this.presenter = presenter

        view.shops_placeholder.builder
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(LinearLayoutManager(view.context))
        setLoadMoreResolver()

        emptyViewHolder = EmptyViewHolder(
                view.context.getString(R.string.refresh),
                view.context.getString(R.string.load_error),
                view.needPermissionLayout) { presenter.refresh() }

        view.btn_back.setOnClickListener {
            //TODO presenter.onBackPressed()
        }
    }

    override fun showProgress(show: Boolean) {
        view.progressLayout.show(show)
    }

    private fun setLoadMoreResolver() {
        view.shops_placeholder.setLoadMoreResolver(LoadMoreView(object : LoadMoreView.Callback {
            override fun onShowMore() {
                presenter.loadMoreChecks()
            }
        }))
    }

    override fun removeAllViews() {
        view.shops_placeholder.removeAllViews()
    }

    override fun loadingDone() {
        view.shops_placeholder.loadingDone()
    }

    override fun noMoreToLoad() {
        view.shops_placeholder.loadingDone()
        view.shops_placeholder.noMoreToLoad()
    }

    override fun showChecks(checks: List<Receipt>, remove: Boolean) {
        if (remove) view.shops_placeholder.removeAllViews()
        loadingDone()

        val dates = presenter.getPlaceholderDates(checks)

        view.shops_placeholder.post {
            checks.forEachIndexed { index, receipt ->
                val dateItem = dates.find { it.after == index }

                if (dateItem != null) {
                    view.shops_placeholder.addView(DateItem(view.context, dateItem.dateTime))
                }

                view.shops_placeholder.addView(
                        CheckItem(view.context, receipt, presenter::onCheckClicked)
                )
            }
        }
    }

    override fun setToolbarTitle(title: String) {
        view.toolbar_title.text = title
    }

}