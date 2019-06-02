package com.djavid.checksonline.features.root

import android.os.Bundle
import com.djavid.checksonline.R
import com.djavid.checksonline.features.base.NewBaseActivity
import javax.inject.Inject

class RootActivity : NewBaseActivity() {

    @Inject
    lateinit var presenter: RootContract.Presenter

    override val layoutResId: Int = R.layout.activity_root

    @Inject
    lateinit var bottomSheet: RootContract.BottomSheet


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.init()

        restoreState(savedInstanceState)
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
//            navigator.applyCommand(Replace(Screens.HOME, null))
        }
    }


//    private val navigator = object : SupportAppNavigator(this, R.id.container) {
//
//        override fun createActivityIntent(screenKey: String?, data: Any?): Intent? =
//                when (screenKey) {
//                    Screens.QR_CODE -> QrActivity.newIntent(this@RootActivity)
//                    Screens.CHECK_ACTIVITY ->
//                        CheckActivity.newIntent(this@RootActivity, data as String)
//                    Screens.HABITS_ACTIVITY -> HabitsActivity.newIntent(this@RootActivity)
//                    Screens.STATS_LIST -> StatsListActivity.newIntent(this@RootActivity,
//                            data as StatsListData)
//                    else -> null
//                }
//
//        override fun createFragment(screenKey: String, data: Any?): Fragment? =
//                when (screenKey) {
//                    Screens.HOME -> ChecksFragment()
//                    Screens.STATS -> StatsFragment()
//                    else -> null
//                }
//
//        override fun unknownScreen(command: Command) {
//            throw IllegalArgumentException("Wrong command: $command")
//        }
//    }
}
