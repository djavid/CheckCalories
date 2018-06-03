package com.djavid.checksonline.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseFragment
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.presenter.check.CheckPresenter
import com.djavid.checksonline.presenter.check.CheckView
import com.djavid.checksonline.utils.parseDate
import com.djavid.checksonline.utils.visible
import com.djavid.checksonline.view.adapters.GoodItem
import kotlinx.android.synthetic.main.fragment_check.*
import kotlinx.android.synthetic.main.toolbar_goods.*
import toothpick.Toothpick
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
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) Toothpick.closeScope(this) //Scopes.HOME
    }

    override fun setGoods(checks: List<Receipt.Item>) {
        goods_placeholder.removeAllViews()
        checks.forEach({
            goods_placeholder.addView(
                    GoodItem(context, it)
            )
        })
    }

    override fun setToolbarSum(sum: Long) {
        price.text = context?.getString(R.string.format_float)
                ?.format(Locale.ROOT, sum / 100f)
    }

    override fun setToolbarAddress(address: String?) {
        if (address == null) {
            iv_location.visible(false)
            tv_address.visible(false)

        } else {
            iv_location.visible(true)
            tv_address.visible(true)
            tv_address.text = address
        }
    }

    override fun setDatetime(date: String?) {
        if (date == null)
            tv_date.visible(false)
        else
            tv_date.text = date.parseDate()
    }

}
