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
import com.djavid.checksonline.presenter.check.CheckActivityPresenter
import com.djavid.checksonline.presenter.check.CheckActivityView
import com.djavid.checksonline.toothpick.modules.CheckModule
import com.djavid.checksonline.view.fragment.CheckFragment
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Replace
import toothpick.Toothpick
import javax.inject.Inject

class CheckActivity : BaseActivity(), CheckActivityView {

    companion object {
        private const val EXTRA_CHECK_ID = "ru.djavid.extras.check_id"
        fun newIntent(ctx: Context, checkId: String) =
                Intent(ctx, CheckActivity::class.java).apply {
                    putExtra(EXTRA_CHECK_ID, checkId)
                }
    }

    @Inject
    lateinit var holder: NavigatorHolder

    @InjectPresenter
    lateinit var presenter: CheckActivityPresenter

    @ProvidePresenter
    fun providePresenter(): CheckActivityPresenter =
            Toothpick.openScopes(application, this)
                    .getInstance(CheckActivityPresenter::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {

        val checkId: String = intent.getStringExtra(EXTRA_CHECK_ID)
            ?: throw IllegalArgumentException("Check id should not be null!")

        Toothpick.inject(this, Toothpick.openScopes(application, this).also {
            it.installModules(CheckModule(checkId))
            println(checkId)
        })

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)

        if (savedInstanceState == null) {
            navigator.applyCommand(Replace(Screens.CHECK, null))
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
        if (isFinishing) Toothpick.closeScope(this)
    }

    private val navigator = object : SupportAppNavigator(this, supportFragmentManager, R.id.container) {

        override fun createActivityIntent(screenKey: String?, data: Any?): Intent? = null

        override fun createFragment(screenKey: String, data: Any?): Fragment? =
                when (screenKey) {
                    Screens.CHECK -> CheckFragment.newInstance()
                    else -> null
                }
    }
}
