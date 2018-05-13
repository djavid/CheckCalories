package com.djavid.checksonline.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseFragment
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.presenter.checks.ChecksPresenter
import com.djavid.checksonline.presenter.checks.ChecksView
import com.djavid.checksonline.view.adapters.CheckItem
import com.djavid.checksonline.view.adapters.LoadMoreView
import kotlinx.android.synthetic.main.fragment_checks.*
import kotlinx.android.synthetic.main.toolbar.*
import toothpick.Toothpick
import java.util.*

class ChecksFragment : BaseFragment(), ChecksView {

    companion object {
        fun newInstance(): ChecksFragment = ChecksFragment()
    }

    @InjectPresenter
    lateinit var presenter: ChecksPresenter

    @ProvidePresenter
    fun providePresenter(): ChecksPresenter =
            Toothpick.openScopes(activity, this) //Scopes.ROOT, Scopes.HOME
                    .getInstance(ChecksPresenter::class.java)

    override val layoutResId get() = R.layout.fragment_checks


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO toolbar
        fab.setOnClickListener({ presenter.onFabClicked() })

        receipts_placeholder.builder
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(LinearLayoutManager(context))

//        receipts_placeholder.setLoadMoreResolver(LoadMoreView(object : LoadMoreView.Callback {
//            override fun onShowMore() {
//                presenter.loadMoreChecks()
//            }
//        }))

        swipeRefreshLayout.setOnRefreshListener { presenter.refresh() }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) Toothpick.closeScope(this) //Scopes.HOME
    }

    override fun setChecks(checks: List<Receipt>) {
        //receipts_placeholder.removeAllViews()
        var totalSum: Long = 0
        checks.forEach({
            receipts_placeholder.addView(
                    CheckItem(context, it, presenter::onCheckClicked)
            )

            totalSum += it.totalSum
        })
        setToolbarSum(totalSum)
    }

    override fun showChecks(checks: List<Receipt>) {
        receipts_placeholder.removeAllViews()
        receipts_placeholder.post({ setChecks(checks) })
    }

    override fun showChecksProgress(show: Boolean, isEmpty: Boolean) {
        swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = show }
    }

    override fun showChecksError(show: Boolean, message: String) {
        showMessage(message)
    }

    override fun loadingDone() {
        receipts_placeholder.loadingDone()
    }

    private fun setToolbarSum(totalSum: Long) {
        price.text = context?.getString(R.string.format_float)
                ?.format(Locale.ROOT, totalSum / 100f)
    }
}
