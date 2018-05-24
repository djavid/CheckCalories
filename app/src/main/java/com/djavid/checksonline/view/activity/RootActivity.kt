package com.djavid.checksonline.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.djavid.checksonline.R
import com.djavid.checksonline.Screens
import com.djavid.checksonline.base.BaseActivity
import com.djavid.checksonline.presenter.root.RootPresenter
import com.djavid.checksonline.presenter.root.RootView
import com.djavid.checksonline.toothpick.modules.RootModule
import com.djavid.checksonline.view.fragment.ChecksFragment
import com.djavid.checksonline.view.fragment.StatsFragment
import kotlinx.android.synthetic.main.activity_root.*
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import toothpick.Toothpick
import javax.inject.Inject

class RootActivity : BaseActivity(), RootView {

    companion object {
        fun newIntent(ctx: Context) = Intent(ctx, RootActivity::class.java)
    }

    @Inject lateinit var holder: NavigatorHolder

    @InjectPresenter lateinit var presenter: RootPresenter

    @ProvidePresenter fun providePresenter(): RootPresenter =
            Toothpick.openScopes(application, this)
                    .getInstance(RootPresenter::class.java)


    private var checksFragment: ChecksFragment? = null
    private var statsFragment: StatsFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScopes(application, this).also {
            it.installModules(RootModule())
        })

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        setupNavigation()
        initFragments()
        restoreState(savedInstanceState)
    }

    private fun initFragments() {
        checksFragment = ChecksFragment.newInstance()
        statsFragment = StatsFragment.newInstance()
    }

    private fun setupNavigation() {
        //navigation.disableShifting()

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_checks -> presenter.onChecksSelected()
                R.id.nav_stats -> presenter.onStatsSelected()
            }
            return@setOnNavigationItemSelectedListener true
        }

        navigation.setOnNavigationItemReselectedListener {

        }
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            navigation.selectedItemId = R.id.nav_checks
            navigator.applyCommand(Replace(Screens.HOME, null))
        } else {

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

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) Toothpick.closeScope(this) //Scopes.ROOT
    }


    private val navigator = object : SupportAppNavigator(this, R.id.container) {

        override fun createActivityIntent(screenKey: String?, data: Any?): Intent? =
                when (screenKey) {
                    Screens.QR_CODE -> QRCodeActivity.newIntent(this@RootActivity)
                    Screens.CHECK_ACTIVITY ->
                        CheckActivity.newIntent(this@RootActivity, data as String)
                    else -> null
                }

        override fun createFragment(screenKey: String, data: Any?): Fragment? =
                when (screenKey) {
                    Screens.HOME -> checksFragment
                    Screens.STATS -> statsFragment
                    else -> null
                }

        override fun unknownScreen(command: Command) {
            throw IllegalArgumentException("Wrong command: $command")
        }
    }
}
