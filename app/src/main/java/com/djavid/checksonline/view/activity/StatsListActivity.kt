package com.djavid.checksonline.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.djavid.checksonline.R
import com.djavid.checksonline.Screens
import com.djavid.checksonline.base.BaseActivity
import com.djavid.checksonline.toothpick.modules.PercentageModule
import com.djavid.checksonline.view.fragment.CategoriesFragment
import com.djavid.checksonline.view.fragment.ShopsFragment
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Replace
import toothpick.Toothpick
import javax.inject.Inject

class StatsListActivity : BaseActivity() {

    companion object {
        private const val EXTRA_TITLE = "ru.djavid.extras.title"
        private const val EXTRA_IS_SHOP = "ru.djavid.extras.is_shop"

        fun newIntent(context: Context, pair: Pair<String, Boolean>) =
                Intent(context, StatsListActivity::class.java).apply {
                    putExtra(EXTRA_TITLE, pair.first)
                    putExtra(EXTRA_IS_SHOP, pair.second)
                }
    }

    @Inject
    lateinit var holder: NavigatorHolder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats_list)

        val title = intent.getStringExtra(EXTRA_TITLE)
                ?: throw IllegalArgumentException("Title should not be null!")
        val isShop = intent.getBooleanExtra(EXTRA_IS_SHOP, false)

        Toothpick.inject(this, Toothpick.openScopes(application, this)
                .also { it.installModules(PercentageModule(title)) })

        if (savedInstanceState == null) {
            if (isShop) navigator.applyCommand(Replace(Screens.SHOPS, null))
            else navigator.applyCommand(Replace(Screens.CATEGORIES, null))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) Toothpick.closeScope(this)
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
                    Screens.CATEGORIES -> CategoriesFragment.newInstance()
                    Screens.SHOPS -> ShopsFragment.newInstance()
                    else -> null
                }
    }
}
