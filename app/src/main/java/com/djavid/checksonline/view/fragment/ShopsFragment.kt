package com.djavid.checksonline.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseFragment
import com.djavid.checksonline.base.EmptyViewHolder
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.presenter.stats.ShopsPresenter
import com.djavid.checksonline.presenter.stats.ShopsView
import com.djavid.checksonline.view.adapters.CheckItem
import kotlinx.android.synthetic.main.fragment_shops.*
import kotlinx.android.synthetic.main.layout_error_action.*
import kotlinx.android.synthetic.main.toolbar_percentages.*
import toothpick.Toothpick

class ShopsFragment : BaseFragment(), ShopsView {

    companion object {
        fun newInstance(): ShopsFragment = ShopsFragment()
    }

    @InjectPresenter
    lateinit var presenter: ShopsPresenter

    @ProvidePresenter
    fun providePresenter(): ShopsPresenter =
            Toothpick.openScopes(activity, this).getInstance(ShopsPresenter::class.java)

    override val layoutResId = R.layout.fragment_shops
    private var emptyViewHolder: EmptyViewHolder? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, Toothpick.openScopes(activity, this))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) Toothpick.closeScope(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        shops_placeholder.builder
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(LinearLayoutManager(context))

        emptyViewHolder = EmptyViewHolder(
                getString(R.string.refresh),
                getString(R.string.load_error),
                needPermissionLayout, { presenter.refresh() })

        btn_back.setOnClickListener { presenter.onBackPressed() }
    }

    override fun showChecks(checks: List<Receipt>) {
        shops_placeholder.removeAllViews()

        shops_placeholder.post({
            checks.forEach({
                shops_placeholder.addView(
                        CheckItem(context, it, presenter::onCheckClicked)
                )
            })
        })
    }

    override fun setToolbarTitle(title: String) {
        toolbar_title.text = title
    }

}
