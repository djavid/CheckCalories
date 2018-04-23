package com.djavid.checksonline.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.djavid.checksonline.R
import com.djavid.checksonline.Screens
import com.djavid.checksonline.base.BaseActivity
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.presenter.root.RootPresenter
import com.djavid.checksonline.presenter.root.RootView
import com.djavid.checksonline.toothpick.Scopes
import com.djavid.checksonline.toothpick.modules.RootModule
import com.djavid.checksonline.view.fragment.CheckFragment
import com.djavid.checksonline.view.fragment.ChecksFragment
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import toothpick.Toothpick
import javax.inject.Inject

class RootActivity : BaseActivity(), RootView {

    @Inject lateinit var holder: NavigatorHolder


    @InjectPresenter lateinit var presenter: RootPresenter

    @ProvidePresenter fun providePresenter(): RootPresenter =
            Toothpick.openScopes(application, this)
                    .getInstance(RootPresenter::class.java)


    private var checksFragment: ChecksFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScopes(application, this).also {
            it.installModules(RootModule())
        })

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        initFragments()
        restoreState(savedInstanceState)
    }

    private fun initFragments() {
        checksFragment = ChecksFragment.newInstance()
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
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
                    else -> null
                }

        override fun unknownScreen(command: Command) {
            throw IllegalArgumentException("Wrong command: $command")
        }
    }
}
