package com.djavid.checksonline.view.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import com.djavid.checksonline.R
import com.djavid.checksonline.model.entities.Percentage
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Position
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View

@Layout(R.layout.item_chart)
class ChartItem(
        private val context: Context?,
        private val percentages: List<Percentage>
) {

    @View(R.id.chart)
    lateinit var chart: PieChart

    @JvmField
    @Position
    var position: Int = 0

    @Resolve
    fun onResolved() {
        setChart()
        setChartData(percentages)
    }

    fun setChartData(list: List<Percentage>) {
        context ?: return

        val entries: MutableList<PieEntry> = mutableListOf()
        var othersSum = 1f

        list.sortedByDescending { it.percentageSum }
                .forEach({
                    println(it.percentageSum * 100)
                    println("            ")
                    if (it.percentageSum * 100 >= 3) {
                        entries.add(PieEntry(it.percentageSum.toFloat() * 100, it.title))
                        othersSum -= it.percentageSum.toFloat()
                    }
                })
        if (othersSum != 0f)
            entries.add(PieEntry(othersSum, "Другие"))

        val pieDataSet = PieDataSet(entries, "")
        val allColors = context.resources.getStringArray(R.array.colorsCategories)
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

    private fun setChart() {
        chart.setEntryLabelColor(Color.WHITE)
        chart.setUsePercentValues(true)
        chart.setDrawEntryLabels(false)
        chart.legend.isEnabled = false
        chart.transparentCircleRadius = 0f
        chart.description = null
        chart.rotationAngle = 90f
        chart.minimumWidth
        chart.setDrawCenterText(true)
        chart.setCenterTextSize(24f)
        chart.setCenterTextColor(Color.parseColor("#f4b854"))
        chart.setCenterTextTypeface(Typeface.SANS_SERIF)
        chart.centerText = generateCenterSpannableText()
        chart.holeRadius = 48f

        chart.isRotationEnabled = false
        chart.animateY(1400, Easing.EasingOption.EaseInOutQuad)
    }

    private fun generateCenterSpannableText(): SpannableString {

        val s = SpannableString("14-20\nмая")

        s.setSpan(StyleSpan(Typeface.BOLD), 0, 5, 0)
        s.setSpan(ForegroundColorSpan(Color.parseColor("#979797")), 5, 9, 0)
        s.setSpan(RelativeSizeSpan(.66f), 5, 9, 0)

        return s
    }

}