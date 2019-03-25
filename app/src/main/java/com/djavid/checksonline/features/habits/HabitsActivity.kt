package com.djavid.checksonline.features.habits

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.djavid.checksonline.R
import com.djavid.checksonline.features.app.Screens
import com.djavid.checksonline.features.base.NewBaseActivity
import com.djavid.checksonline.features.check.activity.CheckActivity
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject

class HabitsActivity : NewBaseActivity() {

    override val layoutResId: Int get() = R.layout.activity_habits

    companion object {
        fun newIntent(context: Context) = Intent(context, HabitsActivity::class.java)
    }

    @Inject
    lateinit var holder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            navigator.applyCommand(Replace(Screens.HABITS, null))
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
                        CheckActivity.newIntent(this@HabitsActivity, data as String)
                    else -> null
                }

        override fun createFragment(screenKey: String, data: Any?): Fragment? =
                when (screenKey) {
                    Screens.HABITS -> HabitsFragment.newInstance()
                    else -> null
                }
    }
}
