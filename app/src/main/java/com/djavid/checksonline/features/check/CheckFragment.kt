package com.djavid.checksonline.features.check

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.djavid.checksonline.R
import com.djavid.checksonline.features.base.BaseFragment
import com.djavid.checksonline.model.entities.Item
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.utils.parseDate
import com.djavid.checksonline.utils.show
import kotlinx.android.synthetic.main.fragment_check.*
import kotlinx.android.synthetic.main.toolbar_goods.*
import java.util.*

class CheckFragment : BaseFragment(), CheckView {

    companion object {
        fun newInstance(): CheckFragment = CheckFragment()
    }

    @InjectPresenter
    lateinit var presenter: CheckPresenter

    @ProvidePresenter
    fun providePresenter(): CheckPresenter =
            Toothpick.openScopes(activity, this).getInstance(CheckPresenter::class.java)

    override val layoutResId get() = R.layout.fragment_check


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, Toothpick.openScopes(activity, this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        goods_placeholder.isNestedScrollingEnabled = false
        goods_placeholder.builder
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(LinearLayoutManager(context))

        btn_back.setOnClickListener { presenter.onBackPressed() }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) Toothpick.closeScope(this) //Scopes.HOME
    }

    override fun setGoods(checks: List<Item>) {
        goods_placeholder.removeAllViews()
        checks.forEach {
            goods_placeholder.addView(
                    GoodItem(context, it)
            )
        }
    }

    override fun setToolbar(receipt: Receipt) {

        //set title
        title.text = receipt.user ?: "Без названия"

        //set sum
        price.text = context?.getString(R.string.format_float)
                ?.format(Locale.ROOT, receipt.totalSum / 100f)

        //set address
        if (receipt.retailPlaceAddress == null) {
            iv_location.show(false)
            tv_address.show(false)
        } else {
            iv_location.show(true)
            tv_address.show(true)
            tv_address.text = receipt.retailPlaceAddress
        }

        //set date
        if (receipt.dateTime == null)
            tv_date.show(false)
        else
            tv_date.text = receipt.dateTime.parseDate()
    }

}
