package com.djavid.checksonline.features.stats_item

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.djavid.checksonline.R
import com.djavid.checksonline.features.root.ViewRoot
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.model.entities.Percentage
import com.djavid.checksonline.utils.getSpannable
import com.djavid.checksonline.utils.show
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.fragment_stat_item.view.*
import kotlinx.android.synthetic.main.layout_progress.view.*
import javax.inject.Inject

class StatsItemView @Inject constructor(
        @ViewRoot private val view: View
) : StatsItemContract.View {

    private lateinit var presenter: StatsItemContract.Presenter
    private var toolbar: Toolbar? = null


    override fun init(presenter: StatsItemContract.Presenter, interval: DateInterval) {
        this.presenter = presenter

        initChart(interval)
        initStatsPlaceholder()
        initSwitchBtn()
    }

    override fun showProgress(show: Boolean) {
        view.progressLayout.show(show)
    }

    private fun initStatsPlaceholder() {
        view.stats_placeholder.isNestedScrollingEnabled = false
        view.stats_placeholder.builder
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(LinearLayoutManager(view.context))
    }

    private fun initChart(interval: DateInterval) {
        view.chart.setEntryLabelColor(Color.WHITE)
        view.chart.setUsePercentValues(true)
        view.chart.setDrawEntryLabels(false)
        view.chart.legend.isEnabled = false
        view.chart.transparentCircleRadius = 0f
        view.chart.description = null
        view.chart.rotationAngle = 90f
        view.chart.minimumWidth
        view.chart.setDrawCenterText(true)
        view.chart.setCenterTextSize(19f)
        view.chart.setCenterTextColor(Color.parseColor("#f4b854"))
        view.chart.setCenterTextTypeface(Typeface.SANS_SERIF)
        view.chart.centerText = interval.getSpannable()
        view.chart.holeRadius = 48f

        view.chart.isRotationEnabled = false
        view.chart.animateY(1400, Easing.EasingOption.EaseInOutQuad)
    }

    private fun initSwitchBtn() {
        view.switch_btn.setOnCheckedChangeListener { _, isChecked ->
            presenter.onSwitchClicked(isChecked)

            if (isChecked && view.context != null)
                view.tv_switch.text = view.context?.getString(R.string.categories)
            else if (!isChecked && view.context != null)
                view.tv_switch.text = view.context!!.getString(R.string.shops)
        }
    }

    override fun setSwitchBtn(checked: Boolean) {
        view.switch_btn.isChecked = checked
    }

    override fun setChartData(list: List<Percentage>) {
        view.context ?: return

        val entries: MutableList<PieEntry> = mutableListOf()
        var othersSum = 1f

        val allColors = view.context?.resources?.getStringArray(R.array.colorsCategories)
        val colorsCustom = mutableListOf<Int>()
        allColors?.forEach { colorsCustom.add(Color.parseColor(it)) }
        val colorGenerator = ColorGenerator.create(colorsCustom)
        val colors = mutableListOf<Int>()

        list.sortedByDescending { it.percentageSum }
                .forEach {
                    if (it.percentageSum * 100 >= 3) {
                        colors.add(colorGenerator.getColor(it.title))
                        entries.add(PieEntry(it.percentageSum.toFloat() * 100, it.title))
                        othersSum -= it.percentageSum.toFloat()
                    }
                }
        if (othersSum != 0f)
            entries.add(PieEntry(othersSum, "Другие"))

        val pieDataSet = PieDataSet(entries, "")
        if (colors.isNotEmpty())
            pieDataSet.colors = colors
        pieDataSet.valueTextColor = Color.WHITE
        pieDataSet.valueTextSize = 15f
        pieDataSet.selectionShift = 10f
        pieDataSet.setDrawValues(false)

        val pieData = PieData(pieDataSet)

        view.chart.data = pieData
        view.chart.invalidate()
        view.chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                presenter.onChartValueSelected(e as PieEntry)
            }

            override fun onNothingSelected() {
                presenter.onNothingSelected()
            }
        })
    }

    override fun setPercentagesData(list: List<Percentage>) {
        view.stats_placeholder.removeAllViews()
        list.forEach {
            view.stats_placeholder.addView(
                    PercentageItem(view.context, it, presenter::onPercentageClicked)
            )
        }
    }


}