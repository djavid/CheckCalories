package com.djavid.checksonline.view.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import com.djavid.checksonline.R
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.model.entities.Percentage
import com.djavid.checksonline.utils.getSpannable
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
        private val interval: DateInterval,
        private var onChartItemResolved: (interval: DateInterval, pos: Int) -> Unit
) {

    @View(R.id.chart)
    lateinit var chart: PieChart

    @JvmField
    @Position
    var position: Int = 0

    @Resolve
    fun onResolved() {
        setChart(interval)
        //setChartData(percentages)

        onChartItemResolved.invoke(interval, position)
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

        chart.post {
            chart.data = pieData
            chart.invalidate()
        }
    }

    private fun setChart(interval: DateInterval) {
        chart.post {
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
    }

}