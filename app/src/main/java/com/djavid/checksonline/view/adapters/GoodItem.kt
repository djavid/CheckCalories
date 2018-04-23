package com.djavid.checksonline.view.adapters

import android.content.Context
import android.widget.TextView
import com.djavid.checksonline.R
import com.djavid.checksonline.model.entities.Receipt
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
        categoryName.text = "Не определено"
    }

}