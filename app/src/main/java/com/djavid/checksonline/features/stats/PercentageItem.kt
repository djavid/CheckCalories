package com.djavid.checksonline.features.stats

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.djavid.checksonline.R
import com.djavid.checksonline.model.entities.Percentage
import com.djavid.checksonline.utils.dpToPx
import com.mindorks.placeholderview.annotations.Click
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Position
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import java.util.*

@Layout(R.layout.item_stat)
class PercentageItem(
        private val context: Context?,
        public val percentage: Percentage,
        private val onClickListener: (Percentage) -> Unit
) {

    @View(R.id.categoryName)
    lateinit var categoryName: TextView

    @View(R.id.tv_sum)
    lateinit var tv_sum: TextView

    @View(R.id.tv_percentage)
    lateinit var tv_percentage: TextView

    @View(R.id.divider)
    lateinit var divider: android.view.View

    @JvmField
    @Position
    var position: Int = 0


    @Resolve
    fun onResolved() {

        val color = getColor(percentage.title)

        setCategoryName(percentage.title, color) //title means shop title also

        tv_sum.text = context?.getString(R.string.format_price)?.format(Locale.ROOT, percentage.sum)

        tv_percentage.apply {
            val percents: Double = percentage.percentageSum * 100
            text = context?.getString(R.string.format_percent)?.format(Locale.ROOT, percents)

            setTextColor(color)
        }

        divider.setBackgroundColor(color)
    }

    @Click(R.id.percentage_card)
    fun onClick() {
        onClickListener.invoke(percentage)
    }

    private fun setCategoryName(category: String?, color: Int) {
        context ?: return

        categoryName.post {
            categoryName.text = category

            val drawable = getRoundDrawable(category, color)

            if (drawable != null) {
                categoryName.background = drawable
            }
        }

    }

    private fun getRoundDrawable(s: String?, color: Int): Drawable? {

        return TextDrawable
                .builder()
                .buildRoundRect("", color, context!!.dpToPx(10))
    }

    private fun getColor(s: String?): Int {

        val allColors = context?.resources?.getStringArray(R.array.colorsCategories)
        val colorsCustom = mutableListOf<Int>()
        allColors?.forEach { colorsCustom.add(Color.parseColor(it)) }
        val generator = ColorGenerator.create(colorsCustom)

        return generator.getColor(s ?: "")

//        val allColors = context?.resources?.getStringArray(R.array.colorsCategories)
//        val color = Color.parseColor(allColors!![Config.labels.indexOf(s)])
    }
}