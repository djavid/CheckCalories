package com.djavid.checksonline.view.adapters

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.djavid.checksonline.R
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.utils.parseTime
import com.mindorks.placeholderview.annotations.*
import java.util.*

@Layout(R.layout.receipt_item)
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
        println("onResolved " + position)

        tv_shop_title.text = receipt.user

        if (receipt.retailPlaceAddress == null || receipt.retailPlaceAddress.isEmpty()) {
            tv_address.visibility = android.view.View.GONE
        } else {
            tv_address.text = receipt.retailPlaceAddress
        }

        tv_sum.text = context?.getString(R.string.format_price)
                ?.format(Locale.ROOT, receipt.totalSum / 100f)
        tv_time.text = parseTime(receipt.dateTime)
        iv_status.setImageResource(R.drawable.check)

    }

    @Click(R.id.receipt_card)
    fun onClick() {
        onClickListener.invoke(receipt)
    }

}