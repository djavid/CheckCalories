package com.djavid.checksonline.features.root

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.djavid.checksonline.R
import com.djavid.checksonline.Screens
import com.djavid.checksonline.features.base.NewBaseActivity
import com.djavid.checksonline.features.check.CheckActivity
import com.djavid.checksonline.features.checks.ChecksFragment
import com.djavid.checksonline.features.habits.HabitsActivity
import com.djavid.checksonline.features.qrcode.QRCodeActivity
import com.djavid.checksonline.features.stats.StatsFragment
import com.djavid.checksonline.features.stats.StatsListActivity
import com.djavid.checksonline.model.entities.StatsListData
import kotlinx.android.synthetic.main.activity_root.*
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject

class RootActivity : NewBaseActivity() {

    @Inject lateinit var holder: NavigatorHolder

    @Inject
    lateinit var presenter: RootContract.Presenter

    private var checksFragment: ChecksFragment? = null
    private var statsFragment: StatsFragment? = null

    override val layoutResId: Int = R.layout.activity_root


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.init()

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


    private val navigator = object : SupportAppNavigator(this, R.id.container) {

        override fun createActivityIntent(screenKey: String?, data: Any?): Intent? =
                when (screenKey) {
                    Screens.QR_CODE -> QRCodeActivity.newIntent(this@RootActivity)
                    Screens.CHECK_ACTIVITY ->
                        CheckActivity.newIntent(this@RootActivity, data as String)
                    Screens.HABITS_ACTIVITY -> HabitsActivity.newIntent(this@RootActivity)
                    Screens.STATS_LIST -> StatsListActivity.newIntent(this@RootActivity,
                            data as StatsListData)
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
