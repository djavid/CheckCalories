package com.djavid.checksonline.view.checks

import android.view.View
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.djavid.checksonline.R
import com.djavid.checksonline.contracts.checks.ChecksContract
import com.djavid.checksonline.model.entities.Dates
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.utils.show
import com.djavid.checksonline.view.check.CheckItem
import com.djavid.checksonline.view.common.EmptyViewHolder
import com.djavid.checksonline.view.common.LoadMoreView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import kotlinx.android.synthetic.main.fragment_checks.view.*
import kotlinx.android.synthetic.main.fragment_checks_content.view.*
import kotlinx.android.synthetic.main.layout_empty_recycler_view.view.*
import kotlinx.android.synthetic.main.layout_error_action.view.*
import kotlinx.android.synthetic.main.toolbar_checks.view.*
import java.util.*

class ChecksView constructor(
		private val viewRoot: View
) : ChecksContract.View {

    private var emptyViewHolder: EmptyViewHolder? = null
    private lateinit var presenter: ChecksContract.Presenter

    override fun init(presenter: ChecksContract.Presenter) {
        this.presenter = presenter

        viewRoot.receipts_placeholder.builder
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(LinearLayoutManager(viewRoot.context))
        setLoadMoreResolver()
        ItemTouchHelper(cardMoveCallback).attachToRecyclerView(viewRoot.receipts_placeholder)

        viewRoot.swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(viewRoot.context, R.color.colorAccent))
        viewRoot.swipeRefreshLayout.setOnRefreshListener { presenter.refresh() }

        emptyViewHolder = EmptyViewHolder(
                viewRoot.context.getString(R.string.refresh),
                viewRoot.context.getString(R.string.load_error),
                viewRoot.needPermissionLayout
        ) { presenter.refresh() }

        setPopupMenu()
	
		viewRoot.checksFragment.setFadeOnClickListener { hidePanel() }
    }
	
	private fun panelHidden() = viewRoot.checksFragment.post { viewRoot.checksFragment.panelState == PanelState.HIDDEN }

    override fun openPanel() {
		viewRoot.checksFragment.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                println(slideOffset)
				if (viewRoot.checksFragment.panelState == PanelState.DRAGGING) {
					viewRoot.checksFragment.isTouchEnabled = false
                }
            }

            override fun onPanelStateChanged(panel: View?, previousState: PanelState?, newState: PanelState?) {

            }
        })
		viewRoot.checksFragment.panelState = PanelState.ANCHORED
    }

    private fun hidePanel() {
		viewRoot.checksFragment.panelState = PanelState.HIDDEN
    }

    private fun setPopupMenu() {
        val popupMenu = PopupMenu(viewRoot.context, viewRoot.btn_period)
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

        viewRoot.btn_period.setOnClickListener {
            popupMenu.show()
        }
    }

    private val cardMoveCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.adapterPosition
            val checkItem = viewRoot.receipts_placeholder.getViewResolverAtPosition(pos) as CheckItem

            Snackbar.make(viewRoot.receipts_placeholder, viewRoot.context?.getString(R.string.check_deleted)!!, Snackbar.LENGTH_SHORT)
                    .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        override fun onShown(transientBottomBar: Snackbar?) {
                            viewRoot.receipts_placeholder.removeView(pos)
                            presenter.removeCheck(checkItem.getId())
                        }
                    })
                    .show()
        }
    }

    override fun setBtnPeriodText(interval: Dates) {
        when (interval) {
            Dates.MONTH -> viewRoot.tv_period.text = viewRoot.context?.getString(R.string.date_month)
            Dates.WEEK -> viewRoot.tv_period.text = viewRoot.context?.getString(R.string.date_week)
            Dates.DAY -> viewRoot.tv_period.text = viewRoot.context?.getString(R.string.date_day)
            Dates.TOTAL -> viewRoot.tv_period.text = viewRoot.context?.getString(R.string.date_total)
            Dates.LAST_DAY -> viewRoot.tv_period.text = viewRoot.context?.getString(R.string.date_last_day)
            Dates.LAST_WEEK -> viewRoot.tv_period.text = viewRoot.context?.getString(R.string.date_last_week)
            Dates.LAST_MONTH -> viewRoot.tv_period.text = viewRoot.context?.getString(R.string.date_last_month)
            else -> {
            }
        }
    }

    override fun showChecks(checks: List<Receipt>) {
        val dates = presenter.getPlaceholderDates(checks)

        viewRoot.receipts_placeholder.post {
            checks.forEachIndexed { index, receipt ->
                val dateItem = dates.find { it.after == index }

                if (dateItem != null) {
                    viewRoot.receipts_placeholder.addView(DateItem(viewRoot.context, dateItem.dateTime))
                }

                viewRoot.receipts_placeholder.addView(
                        CheckItem(viewRoot.context, receipt, presenter::onCheckClicked)
                )
            }
        }
    }

    override fun showChecksProgress(show: Boolean, isEmpty: Boolean) {
        if (show)
            viewRoot.progressAnimation.play()
        else {
            viewRoot.swipeRefreshLayout.isRefreshing = false
            viewRoot.progressAnimation.pause()
        }
    }

    override fun showChecksError(show: Boolean) {
        emptyViewHolder?.show(show)
    }

    override fun showEmptyView(show: Boolean) {
        viewRoot.empty_recycler_layout.show(show)
    }

    override fun loadingDone() {
        viewRoot.receipts_placeholder.loadingDone()
    }

    override fun noMoreToLoad() {
        viewRoot.receipts_placeholder.noMoreToLoad()
    }

    override fun setToolbarSum(totalSum: Double) {
        viewRoot.price.text = viewRoot.context?.getString(R.string.format_float)
                ?.format(Locale.ROOT, totalSum)
    }

    override fun setLoadMoreResolver() {
        viewRoot.receipts_placeholder.setLoadMoreResolver(LoadMoreView(object : LoadMoreView.Callback {
            override fun onShowMore() {
                presenter.loadMoreChecks()
            }
        }))
    }

    override fun removeAllViews() {
        viewRoot.receipts_placeholder.removeAllViews()
    }

}