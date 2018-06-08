package com.djavid.checksonline.view.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.djavid.checksonline.R
import com.djavid.checksonline.model.networking.responses.GetHabitsResponse
import com.djavid.checksonline.utils.dpToPx
import com.djavid.checksonline.utils.visible
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Position
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import java.util.*

@Layout(R.layout.item_good_stat)
class HabitGoodItem(
        private val context: Context?,
        private val item: GetHabitsResponse.Result.PopularItem?
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
        item ?: return

        goodName.text = item.title?.trim()
        totalSum.text = context?.getString(R.string.format_price)
                ?.format(Locale.ROOT, item.totalSum)
        quantity.text = context?.getString(R.string.format_quantity)
                ?.format(Locale.ROOT, item.quantity.toString(), item.price)
        setCategoryName(item.shop)
    }

    private fun setCategoryName(category: String?) {
        context ?: return

        categoryName.post {
            categoryName.text = category ?: "Без названия"

            val drawable = getRoundDrawable(category)
            //val drawable = getColoredDrawable(title)

            if (drawable != null) {
                categoryName.background = drawable
                categoryName.visible(true)
            }
        }

    }

    private fun getRoundDrawable(s: String?): Drawable? {
        s ?: return null
        context ?: return null

        val generator = ColorGenerator.MATERIAL
        val color = generator.getColor(s)

        return TextDrawable
                .builder()
                .buildRoundRect("", color, context.dpToPx(16))
    }

}