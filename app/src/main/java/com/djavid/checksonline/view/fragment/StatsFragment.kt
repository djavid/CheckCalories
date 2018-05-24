package com.djavid.checksonline.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AbsListView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseFragment
import com.djavid.checksonline.model.entities.Percentage
import com.djavid.checksonline.presenter.stats.StatsPresenter
import com.djavid.checksonline.presenter.stats.StatsView
import com.djavid.checksonline.view.adapters.ChartItem
import com.djavid.checksonline.view.adapters.PercentageItem
import kotlinx.android.synthetic.main.fragment_stats.*
import kotlinx.android.synthetic.main.toolbar_stats.*
import toothpick.Toothpick
import java.util.*

class StatsFragment : BaseFragment(), StatsView {

    companion object {
        fun newInstance(): StatsFragment = StatsFragment()
    }

    @InjectPresenter
    lateinit var presenter: StatsPresenter

    @ProvidePresenter
    fun providePresenter(): StatsPresenter =
            Toothpick.openScopes(activity, this)
                    .getInstance(StatsPresenter::class.java)

    override val layoutResId = R.layout.fragment_stats

    private var currentPosition = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, Toothpick.openScopes(activity, this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initStatsPlaceholder()
        initChartsPlaceholder()

        switch_btn.setOnCheckedChangeListener { buttonView, isChecked ->
            presenter.onSwitchClicked(isChecked)
            if (isChecked && context != null)
                tv_switch.text = context!!.getString(R.string.categories)
            else if (!isChecked && context != null)
                tv_switch.text = context!!.getString(R.string.shops)
        }
    }

    private fun initStatsPlaceholder() {
        stats_placeholder.isNestedScrollingEnabled = false
        stats_placeholder.builder
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(LinearLayoutManager(context))
    }

    private fun initChartsPlaceholder() {

        //charts_placeholder.isNestedScrollingEnabled = false

        val layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, true)
        charts_placeholder.builder
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(layoutManager)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(charts_placeholder)

        charts_placeholder.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    val centerView = snapHelper.findSnapView(layoutManager)
                    val pos = layoutManager.getPosition(centerView)

                    if (pos != currentPosition) {
                        currentPosition = pos
                        presenter.onChartItemScrolled(pos)
                    }
                }
            }
        })
    }

    override fun setChartData(list: List<Percentage>) {
        context ?: return

        charts_placeholder.addView(ChartItem(context, list))
        charts_placeholder.addView(ChartItem(context, list))
    }

    override fun refreshCurrentChartData(list: List<Percentage>) {
        if (currentPosition < charts_placeholder.allViewResolvers.size
                && currentPosition >= 0) {
            val item = charts_placeholder.allViewResolvers[currentPosition] as ChartItem
            item.setChartData(list)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) Toothpick.closeScope(this)
    }


    override fun setPercentages(list: List<Percentage>) {
        stats_placeholder.removeAllViews()
        list.forEach({
            stats_placeholder.addView(
                    PercentageItem(context, it)
            )
        })
    }

    override fun setToolbarSum(totalSum: Double) {
        price.text = context?.getString(R.string.format_float)?.format(Locale.ROOT, totalSum)
    }

}
