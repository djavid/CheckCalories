package com.djavid.checksonline.view.fragment

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseFragment
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.model.entities.Percentage
import com.djavid.checksonline.presenter.stats.StatsItemPresenter
import com.djavid.checksonline.presenter.stats.StatsItemView
import com.djavid.checksonline.toothpick.modules.StatsItemModule
import com.djavid.checksonline.utils.getSpannable
import com.djavid.checksonline.view.adapters.PercentageItem
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_stat_item.*
import toothpick.Toothpick

class StatItemFragment : BaseFragment(), StatsItemView {

    companion object {

        private const val ARG_INTERVAL = "interval"
        private const val ARG_DATE_START = "date_start"
        private const val ARG_DATE_END = "date_end"

        fun newInstance(interval: DateInterval): StatItemFragment {

            val bundle = Bundle()
            bundle.putString(ARG_INTERVAL, interval.interval)
            bundle.putString(ARG_DATE_START, interval.dateStart)
            bundle.putString(ARG_DATE_END, interval.dateEnd)

            return StatItemFragment().apply { arguments = bundle }
        }
    }

    @InjectPresenter
    lateinit var presenter: StatsItemPresenter

    @ProvidePresenter
    fun providePresenter(): StatsItemPresenter =
            Toothpick.openScopes(activity, this).also {
                val interval = DateInterval(
                        arguments?.getString(ARG_INTERVAL) ?: "",
                        arguments?.getString(ARG_DATE_START) ?: "",
                        arguments?.getString(ARG_DATE_END) ?: ""
                )
                it.installModules(StatsItemModule(interval))

            }.getInstance(StatsItemPresenter::class.java)

    override val layoutResId = R.layout.fragment_stat_item


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, Toothpick.openScopes(activity, this))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) Toothpick.closeScope(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val interval = DateInterval(
                arguments?.getString(ARG_INTERVAL) ?: "",
                arguments?.getString(ARG_DATE_START) ?: "",
                arguments?.getString(ARG_DATE_END) ?: ""
        )

        initChart(interval)
        initStatsPlaceholder()
        initSwitchBtn()
    }


    private fun initStatsPlaceholder() {
        stats_placeholder.isNestedScrollingEnabled = false
        stats_placeholder.builder
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(LinearLayoutManager(context))
    }

    private fun initChart(interval: DateInterval) {
        chart.setEntryLabelColor(Color.WHITE)
        chart.setUsePercentValues(true)
        chart.setDrawEntryLabels(false)
        chart.legend.isEnabled = false
        chart.transparentCircleRadius = 0f
        chart.description = null
        chart.rotationAngle = 90f
        chart.minimumWidth
        chart.setDrawCenterText(true)
        chart.setCenterTextSize(19f)
        chart.setCenterTextColor(Color.parseColor("#f4b854"))
        chart.setCenterTextTypeface(Typeface.SANS_SERIF)
        chart.centerText = interval.getSpannable()
        chart.holeRadius = 48f

        chart.isRotationEnabled = false
        chart.animateY(1400, Easing.EasingOption.EaseInOutQuad)
    }

    private fun initSwitchBtn() {
        switch_btn.setOnCheckedChangeListener { _, isChecked ->
            presenter.onSwitchClicked(isChecked)

            if (isChecked && context != null)
                tv_switch.text = context?.getString(R.string.categories)
            else if (!isChecked && context != null)
                tv_switch.text = context!!.getString(R.string.shops)
        }
    }

    override fun setSwitchBtn(checked: Boolean) {
        switch_btn.isChecked = checked
    }

    override fun setChartData(list: List<Percentage>) {
        context ?: return

        val entries: MutableList<PieEntry> = mutableListOf()
        var othersSum = 1f

        list.sortedByDescending { it.percentageSum }
                .forEach({
                    if (it.percentageSum * 100 >= 3) {
                        entries.add(PieEntry(it.percentageSum.toFloat() * 100, it.title))
                        othersSum -= it.percentageSum.toFloat()
                    }
                })
        if (othersSum != 0f)
            entries.add(PieEntry(othersSum, "Другие"))

        val pieDataSet = PieDataSet(entries, "")
        val allColors = context?.resources?.getStringArray(R.array.colorsCategories)
        val colors = mutableListOf<Int>()
        allColors?.forEach {
            colors.add(Color.parseColor(it))
        }
        if (colors.isNotEmpty())
            pieDataSet.colors = colors
        pieDataSet.valueTextColor = Color.WHITE
        pieDataSet.valueTextSize = 15f
        pieDataSet.selectionShift = 10f
        pieDataSet.setDrawValues(false)

        val pieData = PieData(pieDataSet)

        chart.data = pieData
        chart.invalidate()
    }

    override fun setPercentagesData(list: List<Percentage>) {
        stats_placeholder.removeAllViews()
        list.forEach({
            stats_placeholder.addView(
                    PercentageItem(context, it)
            )
        })
    }

}
