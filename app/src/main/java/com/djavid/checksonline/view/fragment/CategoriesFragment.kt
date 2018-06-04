package com.djavid.checksonline.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseFragment
import com.djavid.checksonline.base.EmptyViewHolder
import com.djavid.checksonline.model.entities.StatItem
import com.djavid.checksonline.presenter.stats.CategoriesPresenter
import com.djavid.checksonline.presenter.stats.CategoriesView
import com.djavid.checksonline.view.adapters.GoodStatItem
import com.djavid.checksonline.view.adapters.LoadMoreView
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.layout_error_action.*
import kotlinx.android.synthetic.main.toolbar_percentages.*
import toothpick.Toothpick

class CategoriesFragment : BaseFragment(), CategoriesView {

    companion object {
        fun newInstance(): CategoriesFragment = CategoriesFragment()
    }

    @InjectPresenter
    lateinit var presenter: CategoriesPresenter

    @ProvidePresenter
    fun providePresenter(): CategoriesPresenter =
            Toothpick.openScopes(activity, this).getInstance(CategoriesPresenter::class.java)

    override val layoutResId = R.layout.fragment_categories
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
        categories_placeholder.builder
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(LinearLayoutManager(context))
        setLoadMoreResolver()

        emptyViewHolder = EmptyViewHolder(
                getString(R.string.refresh),
                getString(R.string.load_error),
                needPermissionLayout, { presenter.refresh() })

        btn_back.setOnClickListener { presenter.onBackPressed() }
    }

    private fun setLoadMoreResolver() {
        categories_placeholder.setLoadMoreResolver(LoadMoreView(object : LoadMoreView.Callback {
            override fun onShowMore() {
                presenter.loadMoreChecks()
            }
        }))
    }

    override fun removeAllViews() {
        categories_placeholder.removeAllViews()
    }

    override fun loadingDone() {
        categories_placeholder.loadingDone()
    }

    override fun noMoreToLoad() {
        categories_placeholder.loadingDone()
        categories_placeholder.noMoreToLoad()
    }

    override fun showItems(items: List<StatItem>, remove: Boolean) {
        if (remove) categories_placeholder.removeAllViews()
        loadingDone()

        categories_placeholder.post({
            items.forEach({
                categories_placeholder.addView(
                        GoodStatItem(context, it)
                )
            })
        })
    }

    override fun setToolbarTitle(title: String) {
        toolbar_title.text = title
    }

}
