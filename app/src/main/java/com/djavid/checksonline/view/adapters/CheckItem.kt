package com.djavid.checksonline.view.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import com.djavid.checksonline.R
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.utils.parseTime
import com.mindorks.placeholderview.annotations.*
import java.util.*
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator

@Layout(R.layout.item_receipt)
class CheckItem(
        private val context: Context?,
        private val receipt: Receipt,
        private val onClickListener: (Receipt) -> Unit
) {

    @View(R.id.iv_logo_circle)
    lateinit var v_logo_circle: ImageView

    @View(R.id.tv_shop_title)
    lateinit var tv_shop_title: TextView

    @View(R.id.tv_address)
    lateinit var tv_address: TextView

    @View(R.id.tv_sum)
    lateinit var tv_sum: TextView

    @View(R.id.tv_time)
    lateinit var tv_time: TextView

    @View(R.id.iv_status)
    lateinit var iv_status: ImageView

    @JvmField
    @Position
    var position: Int = 0


    @Resolve
    fun onResolved() {

        val drawable = getRoundDrawable(receipt.user?.get(0)?.toString())
        if (drawable != null) v_logo_circle.setImageDrawable(drawable)

        tv_shop_title.text = receipt.user

        if (receipt.retailPlaceAddress == null || receipt.retailPlaceAddress.isEmpty()) {
            tv_address.visibility = android.view.View.GONE
        } else {
            tv_address.text = receipt.retailPlaceAddress
        }

        tv_sum.text = context?.getString(R.string.format_price)
                ?.format(Locale.ROOT, receipt.totalSum / 100f)
        tv_time.text = receipt.dateTime?.parseTime()
        iv_status.setImageResource(R.drawable.ic_check)

    }

    @Click(R.id.receipt_card)
    fun onClick() {
        onClickListener.invoke(receipt)
    }

    private fun getRoundDrawable(s: String?): Drawable? {
        s ?: return null
        val generator = ColorGenerator.MATERIAL
        val color = generator.getColor(s)

        return TextDrawable
                .builder()
                .buildRound(s, color)
    }

}