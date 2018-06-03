package com.djavid.checksonline.view.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.djavid.checksonline.R
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.utils.formatShopTitle
import com.djavid.checksonline.utils.parseTime
import com.djavid.checksonline.utils.transformations.CircleTransformation
import com.mindorks.placeholderview.annotations.*
import com.squareup.picasso.Picasso
import java.util.*

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

    @View(R.id.iv_location)
    lateinit var iv_location: ImageView

    @JvmField
    @Position
    var position: Int = 0


    @Resolve
    fun onResolved() {
        context ?: return

        val shopTitle = receipt.user?.formatShopTitle()
                ?: context.getString(R.string.shop_no_title)
        tv_shop_title.text = shopTitle

        if (receipt.logo == null || receipt.logo.isEmpty()) {
            val drawable = getRoundDrawable(shopTitle[0].toString())
            if (drawable != null) v_logo_circle.setImageDrawable(drawable)
        } else {
            Picasso.get()
                    .load(receipt.logo)
                    .fit()
                    .transform(CircleTransformation())
                    .into(v_logo_circle)
        }

        if (receipt.retailPlaceAddress == null || receipt.retailPlaceAddress.isEmpty()) {
            tv_address.visibility = android.view.View.GONE
            iv_location.visibility = android.view.View.GONE
        } else {
            tv_address.text = receipt.retailPlaceAddress
        }

        tv_sum.text = context.getString(R.string.format_price)
                ?.format(Locale.ROOT, receipt.totalSum / 100f)
        tv_time.text = receipt.dateTime?.parseTime()
    }

    @Click(R.id.receipt_card)
    fun onClick() {
        onClickListener.invoke(receipt)
    }

    private fun getRoundDrawable(s: String?): Drawable? {
        s ?: return null

        val generator = ColorGenerator.MATERIAL
        val color = generator.getColor(s)
        //val color = Color.parseColor("#9b9b9b")

        return TextDrawable
                .builder()
                .beginConfig()
                .bold()
                .endConfig()
                .buildRound(s, color)
    }

}