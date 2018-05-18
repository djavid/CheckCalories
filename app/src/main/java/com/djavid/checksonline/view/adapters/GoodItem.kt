package com.djavid.checksonline.view.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.djavid.checksonline.R
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.utils.Config
import com.djavid.checksonline.utils.dpToPx
import com.djavid.checksonline.utils.visible
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Position
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import java.util.*

@Layout(R.layout.good_item)
class GoodItem(
        private val context: Context?,
        private val good: Receipt.Item
) {

    @View(R.id.goodName)
    lateinit var goodName: TextView

    @View(R.id.categoryName)
    lateinit var categoryName: TextView

    @View(R.id.totalSum)
    lateinit var totalSum: TextView

    @View(R.id.quantity)
    lateinit var quantity: TextView

    @JvmField
    @Position
    var position: Int = 0


    @Resolve
    fun onResolved() {
        goodName.text = good.name?.trim()
        totalSum.text = context?.getString(R.string.format_price)
                ?.format(Locale.ROOT, good.sum / 100f)
        quantity.text = context?.getString(R.string.format_quantity)
                ?.format(Locale.ROOT, good.quantity.toString(), good.price / 100f)
        setCategoryName(good.category)
    }

    fun setCategoryName(category: String?) {
        context ?: return

        categoryName.post {
            categoryName.text = category ?: "Не определено"

            val drawable = getRoundDrawable(category)
            //val drawable = getColoredDrawable(category)

            if (drawable != null) {
                categoryName.background = drawable
                categoryName.visible(true)
            }
        }

    }

    fun setName(name: String) {
        goodName.post { goodName.text = name }
    }


    private fun getColoredDrawable(s: String): Drawable? {
        context ?: return null

        val allColors = context.resources.getStringArray(R.array.colorsCategories)
        val color = Color.parseColor(allColors!![Config.labels.indexOf(s)])

        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(color)
            cornerRadius = context.dpToPx(8).toFloat()
        }
    }

    private fun getRoundDrawable(s: String?): Drawable? {
        s ?: return null
        context ?: return null

        val generator = ColorGenerator.MATERIAL
        val color = generator.getColor(s)

        return TextDrawable
                .builder()
                .buildRoundRect("", color, context.dpToPx(10))
    }

}