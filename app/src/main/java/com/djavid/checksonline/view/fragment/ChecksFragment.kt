package com.djavid.checksonline.view.fragment

import android.os.Bundle
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.PopupMenu
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseFragment
import com.djavid.checksonline.base.Dates
import com.djavid.checksonline.base.EmptyViewHolder
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.presenter.checks.ChecksPresenter
import com.djavid.checksonline.presenter.checks.ChecksView
import com.djavid.checksonline.utils.visible
import com.djavid.checksonline.view.adapters.CheckItem
import com.djavid.checksonline.view.adapters.DateItem
import com.djavid.checksonline.view.adapters.LoadMoreView
import kotlinx.android.synthetic.main.fragment_checks.*
import kotlinx.android.synthetic.main.layout_empty_recycler_view.*
import kotlinx.android.synthetic.main.layout_error_action.*
import kotlinx.android.synthetic.main.toolbar_checks.*
import toothpick.Toothpick
import java.util.*

class ChecksFragment : BaseFragment(), ChecksView {

    companion object {
        fun newInstance(): ChecksFragment = ChecksFragment()
    }

    @InjectPresenter
    lateinit var presenter: ChecksPresenter

    @ProvidePresenter
    fun providePresenter(): ChecksPresenter =
            Toothpick.openScopes(activity, this) //Scopes.ROOT, Scopes.HOME
                    .getInstance(ChecksPresenter::class.java)

    override val layoutResId get() = R.layout.fragment_checks
    private var emptyViewHolder: EmptyViewHolder? = null


    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) Toothpick.closeScope(this) //Scopes.HOME
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fab.setOnClickListener({ presenter.onFabClicked() })

        receipts_placeholder.builder
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(LinearLayoutManager(context))
        setLoadMoreResolver()
        ItemTouchHelper(cardMoveCallback).attachToRecyclerView(receipts_placeholder)

        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorAccent))
        swipeRefreshLayout.setOnRefreshListener {
            presenter.refresh()
        }

        emptyViewHolder = EmptyViewHolder(
                getString(R.string.refresh),
                getString(R.string.load_error),
                needPermissionLayout, { presenter.refresh() })

        setPopupMenu()
    }


    override fun showChecks(checks: List<Receipt>) {

        val dates = presenter.getPlaceholderDates(checks)

        receipts_placeholder.post {
            checks.forEachIndexed { index, receipt ->
                val dateItem = dates.find { it.after == index }

                if (dateItem != null) {
                    receipts_placeholder.addView(DateItem(context, dateItem.dateTime))
                }

                receipts_placeholder.addView(
                        CheckItem(context, receipt, presenter::onCheckClicked)
                )
            }
        }
    }

    override fun showChecksProgress(show: Boolean, isEmpty: Boolean) {
        swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = show }
    }

    override fun showChecksError(show: Boolean) {
        emptyViewHolder?.show(show)
    }

    override fun showEmptyView(show: Boolean) {
        empty_recycler_layout.visible(show)
    }

    override fun loadingDone() {
        receipts_placeholder.loadingDone()
    }

    override fun noMoreToLoad() {
        receipts_placeholder.noMoreToLoad()
    }

    override fun setToolbarSum(totalSum: Double) {
        price.text = context?.getString(R.string.format_float)
                ?.format(Locale.ROOT, totalSum)
    }

    override fun setLoadMoreResolver() {
        receipts_placeholder.setLoadMoreResolver(LoadMoreView(object : LoadMoreView.Callback {
            override fun onShowMore() {
                presenter.loadMoreChecks()
            }
        }))
    }

    override fun removeAllViews() {
        receipts_placeholder.removeAllViews()
    }

    private fun setPopupMenu() {
        val popupMenu = PopupMenu(context, btn_period)
        popupMenu.inflate(R.menu.menu_checks_date_popup)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_total -> {
                    presenter.onDateIntervalChosen(Dates.TOTAL)
                    true
                }
                R.id.menu_day -> {
                    presenter.onDateIntervalChosen(Dates.DAY)
                    true
                }
                R.id.menu_week -> {
                    presenter.onDateIntervalChosen(Dates.WEEK)
                    true
                }
                R.id.menu_month -> {
                    presenter.onDateIntervalChosen(Dates.MONTH)
                    true
                }
                R.id.menu_last_day -> {
                    presenter.onDateIntervalChosen(Dates.LAST_DAY)
                    true
                }
                R.id.menu_last_week -> {
                    presenter.onDateIntervalChosen(Dates.LAST_WEEK)
                    true
                }
                R.id.menu_last_month -> {
                    presenter.onDateIntervalChosen(Dates.LAST_MONTH)
                    true
                }
                else -> false
            }
        }

        btn_period.setOnClickListener {
            popupMenu.show()
        }
    }

    override fun setBtnPeriodText(interval: Dates) {
        when (interval) {
            Dates.MONTH -> tv_period.text = context?.getString(R.string.date_month)
            Dates.WEEK -> tv_period.text = context?.getString(R.string.date_week)
            Dates.DAY -> tv_period.text = context?.getString(R.string.date_day)
            Dates.TOTAL -> tv_period.text = context?.getString(R.string.date_total)
            Dates.LAST_DAY -> tv_period.text = context?.getString(R.string.date_last_day)
            Dates.LAST_WEEK -> tv_period.text = context?.getString(R.string.date_last_week)
            Dates.LAST_MONTH -> tv_period.text = context?.getString(R.string.date_last_month)
            else -> {}
        }
    }

    private val cardMoveCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.adapterPosition
            val checkItem = receipts_placeholder.getViewResolverAtPosition(pos) as CheckItem

            Snackbar.make(receipts_placeholder, context?.getString(R.string.check_deleted)!!, Snackbar.LENGTH_SHORT)
                    .addCallback(object: BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        override fun onShown(transientBottomBar: Snackbar?) {
                            receipts_placeholder.removeView(pos)
                            presenter.removeCheck(checkItem.getId())
                        }
                    })
                    .show()
        }
    }
}
