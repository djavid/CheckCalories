package com.djavid.checksonline.view.check

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.djavid.checksonline.R
import com.djavid.checksonline.contracts.check.CheckContract
import com.djavid.checksonline.model.entities.Item
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.utils.DrawableGenerator
import com.djavid.checksonline.utils.parseDate
import com.djavid.checksonline.utils.show
import kotlinx.android.synthetic.main.fragment_check.view.*
import kotlinx.android.synthetic.main.layout_progress.view.*
import kotlinx.android.synthetic.main.toolbar_goods.view.*
import java.util.*

class CheckView constructor(
        private val viewRoot: View,
        private val generator: DrawableGenerator,
        private val goodsAdapter: GoodsAdapter
) : CheckContract.View {
    
    private lateinit var presenter: CheckContract.Presenter
    
    override fun init(presenter: CheckContract.Presenter) {
        this.presenter = presenter

//        viewRoot.nestedScroll.isFocusable = fa
    
        viewRoot.goods_placeholder.apply {
            show(true)
            layoutManager = LinearLayoutManager(viewRoot.context, RecyclerView.VERTICAL, false)
            adapter = goodsAdapter
        }
    }
    
    override fun showGoods(goods: List<Item>) {
        goodsAdapter.showGoods(goods)
    }
    
    override fun setTitle(title: String) {
        viewRoot.checkTitle.text = title
    }
    
    override fun setAddress(address: String) {
        viewRoot.checkAddress.text = address
    }
    
    override fun setTotalSum(sum: Float) {
        val text = viewRoot.context.getString(R.string.format_price).format(sum)
        viewRoot.checkTotalSum.text = text
    }
    
    override fun setLogo(shopTitle: String) {
        val roundDrawable = generator.getRoundDrawable(shopTitle)
        viewRoot.checkLogo.setImageDrawable(roundDrawable)
    }
    
    override fun setToolbar(receipt: Receipt) {
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

    override fun showProgress(show: Boolean) {
        viewRoot.progressLayout.show(show)
    }

}