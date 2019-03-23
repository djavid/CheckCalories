package com.djavid.checksonline.features.habits.items

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.djavid.checksonline.R
import com.djavid.checksonline.model.networking.responses.GetHabitsResponse
import com.djavid.checksonline.utils.formatShopTitle
import com.djavid.checksonline.utils.show
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Position
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import java.util.*

@Layout(R.layout.item_receipt)
class PopularShopItem(
        private val context: Context?,
        private val shop: GetHabitsResponse.Result.PopularShop?
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

    @View(R.id.iv_share)
    lateinit var iv_status: ImageView

    @View(R.id.iv_location)
    lateinit var iv_location: ImageView

    @JvmField
    @Position
    var position: Int = 0


    @Resolve
    fun onResolved() {
        context ?: return
        shop ?: return

        val shopTitle = shop.title?.formatShopTitle()
                ?: context.getString(R.string.shop_no_title)
        tv_shop_title.text = shopTitle

        val drawable = getRoundDrawable(shopTitle[0].toString())
        if (drawable != null) v_logo_circle.setImageDrawable(drawable)

        if (shop.address == null || shop.address.isEmpty()) {
            tv_address.show(false)
            iv_location.show(false)
        } else {
            tv_address.text = shop.address
        }

        tv_sum.text = context.getString(R.string.format_price).format(Locale.ROOT, shop.sum)
        tv_time.show(false)
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