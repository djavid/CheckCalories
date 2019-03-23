package com.djavid.checksonline.features.check.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.djavid.checksonline.R
import com.djavid.checksonline.features.app.Screens
import com.djavid.checksonline.features.base.NewBaseActivity
import com.djavid.checksonline.features.check.fragment.CheckFragment
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject

const val EXTRA_CHECK_ID = "com.djavid.extras.check_id"

class CheckActivity : NewBaseActivity() {

    companion object {
        fun newIntent(ctx: Context, checkId: String) =
                Intent(ctx, CheckActivity::class.java).apply {
                    putExtra(EXTRA_CHECK_ID, checkId)
                }
    }

    @Inject
    lateinit var holder: NavigatorHolder

    @Inject
    lateinit var presenter: CheckActivityContract.Presenter

    override val layoutResId: Int get() = R.layout.activity_check


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val checkId: String = intent.getStringExtra(EXTRA_CHECK_ID)
                ?: throw IllegalArgumentException("Check id should not be null!")
        presenter.init(checkId)

        if (savedInstanceState == null) {
            navigator.applyCommand(Replace(Screens.CHECK, checkId))
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

    private val navigator = object : SupportAppNavigator(this, supportFragmentManager, R.id.container) {

        override fun createActivityIntent(screenKey: String?, data: Any?): Intent? = null

        override fun createFragment(screenKey: String, data: Any?): Fragment? =
                when (screenKey) {
                    Screens.CHECK -> CheckFragment.newInstance().apply {
                        (data as? String)?.let {
                            this.arguments?.putString(EXTRA_CHECK_ID, it)
                        }
                    }
                    else -> null
                }
    }
}