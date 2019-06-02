package com.djavid.checksonline.features.check_new

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.djavid.checksonline.R
import com.djavid.checksonline.features.check.GoodItem
import com.djavid.checksonline.features.root.ViewRoot
import com.djavid.checksonline.model.entities.Item
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.utils.parseDate
import com.djavid.checksonline.utils.show
import kotlinx.android.synthetic.main.fragment_check.view.*
import kotlinx.android.synthetic.main.layout_progress.view.*
import kotlinx.android.synthetic.main.toolbar_goods.view.*
import java.util.*
import javax.inject.Inject

class NewCheckView @Inject constructor(
        @ViewRoot val viewRoot: ViewGroup
) : NewCheckContract.View {

    private lateinit var presenter: NewCheckContract.Presenter

    override fun init(presenter: NewCheckContract.Presenter) {
        this.presenter = presenter

        viewRoot.goods_placeholder.isNestedScrollingEnabled = false
        viewRoot.goods_placeholder.builder
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(LinearLayoutManager(viewRoot.context))
    }

    override fun showReceipt(receipt: Receipt) {
        setGoods(receipt.items)
        //setToolbar(receipt)
    }

    private fun setGoods(checks: List<Item>) {
        viewRoot.goods_placeholder.removeAllViews()
        checks.forEach {
            viewRoot.goods_placeholder.addView(
                    GoodItem(viewRoot.context, it)
            )
        }
    }

    private fun setToolbar(receipt: Receipt) {
        //set title
        viewRoot.title.text = receipt.user ?: viewRoot.context.getString(R.string.no_title)

        //set sum
        viewRoot.price.text = viewRoot.context?.getString(R.string.format_float)
                ?.format(Locale.ROOT, receipt.totalSum / 100f)

        //set address
        if (receipt.retailPlaceAddress == null) {
            viewRoot.iv_location.show(false)
            viewRoot.tv_address.show(false)
        } else {
            viewRoot.iv_location.show(true)
            viewRoot.tv_address.show(true)
            viewRoot.tv_address.text = receipt.retailPlaceAddress
        }

        //set date
        if (receipt.dateTime == null)
            viewRoot.tv_date.show(false)
        else
            viewRoot.tv_date.text = receipt.dateTime.parseDate()
    }

    private fun showProgress(show: Boolean) {
        viewRoot.progressLayout.show(show)
    }

}