package com.djavid.checksonline.features.stats

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import com.djavid.checksonline.R
import com.djavid.checksonline.features.app.Screens
import com.djavid.checksonline.features.base.NewBaseActivity
import com.djavid.checksonline.features.categories.CategoriesFragment
import com.djavid.checksonline.features.check.activity.CheckActivity
import com.djavid.checksonline.features.shops.ShopsFragment
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.model.entities.StatsListData
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject

const val EXTRA_DATE_INTERVAL = "ru.djavid.extras.date_interval"

class StatsListActivity : NewBaseActivity() {

    companion object {
        private const val EXTRA_TITLE = "ru.djavid.extras.title"
        private const val EXTRA_IS_SHOP = "ru.djavid.extras.is_shop"
        private const val EXTRA_DATE_START = "ru.djavid.extras.date_start"
        private const val EXTRA_DATE_END = "ru.djavid.extras.date_end"

        fun newIntent(context: Context, data: StatsListData) =
                Intent(context, StatsListActivity::class.java).apply {
                    putExtra(EXTRA_TITLE, data.title)
                    putExtra(EXTRA_IS_SHOP, data.isShop)
                    putExtra(EXTRA_DATE_START, data.interval.dateStart)
                    putExtra(EXTRA_DATE_END, data.interval.dateEnd)
                    putExtra(EXTRA_DATE_INTERVAL, data.interval.interval)
                }
    }

    @Inject
    lateinit var holder: NavigatorHolder

    override val layoutResId: Int get() = R.layout.activity_stats_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra(EXTRA_TITLE)
                ?: throw IllegalArgumentException("Title should not be null!")
        val isShop = intent.getBooleanExtra(EXTRA_IS_SHOP, false)

        val dateStart = intent.getStringExtra(EXTRA_DATE_START)
        val dateEnd = intent.getStringExtra(EXTRA_DATE_END)
        val interval = intent.getStringExtra(EXTRA_DATE_INTERVAL)
        val dateInterval = DateInterval(interval, dateStart, dateEnd)

        if (savedInstanceState == null) {
            if (isShop) navigator.applyCommand(Replace(Screens.SHOPS, dateInterval))
            else navigator.applyCommand(Replace(Screens.CATEGORIES, dateInterval))
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        holder.setNavigator(navigator)
    }

    override fun onPause() {
        holder.removeNavigator()
        super.onPause()
    }

    private val navigator = object : SupportAppNavigator(this,
            supportFragmentManager, R.id.container) {

        override fun createActivityIntent(screenKey: String?, data: Any?): Intent? =
                when (screenKey) {
                    Screens.CHECK_ACTIVITY ->
                        CheckActivity.newIntent(this@StatsListActivity, data as String)
                    else -> null
                }

        override fun createFragment(screenKey: String, data: Any?): Fragment? =
                when (screenKey) {
                    Screens.CATEGORIES -> CategoriesFragment.newInstance().apply {
                        arguments?.putParcelable(EXTRA_DATE_INTERVAL, data as Parcelable)
                    }
                    Screens.SHOPS -> ShopsFragment.newInstance().apply {
                        arguments?.putParcelable(EXTRA_DATE_INTERVAL, data as Parcelable)
                    }
                    else -> null
                }
    }
}
