package com.djavid.checksonline.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.djavid.checksonline.R
import com.djavid.checksonline.Screens
import com.djavid.checksonline.base.BaseActivity
import com.djavid.checksonline.view.fragment.CategoriesFragment
import com.djavid.checksonline.view.fragment.ShopsFragment
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportAppNavigator
import toothpick.Toothpick
import javax.inject.Inject

class StatsListActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context) = Intent(context, StatsListActivity::class.java)
    }

    @Inject
    lateinit var holder: NavigatorHolder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats_list)

        Toothpick.inject(this, Toothpick.openScopes(application, this))
        //.also { it.installModules(CheckModule(checkId)) })

        if (savedInstanceState == null) {
            //navigator.applyCommand(Replace(Screens.HABITS, null)) //TODO
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

        override fun createActivityIntent(screenKey: String?, data: Any?): Intent? = null

        override fun createFragment(screenKey: String, data: Any?): Fragment? =
                when (screenKey) {
                    Screens.CATEGORIES -> CategoriesFragment.newInstance()
                    Screens.SHOPS -> ShopsFragment.newInstance()
                    else -> null
                }
    }
}
