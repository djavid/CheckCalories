package com.djavid.checksonline.view.habits

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseActivity

class HabitsActivity : BaseActivity() {

    override val layoutResId: Int get() = R.layout.activity_habits

    companion object {
        fun newIntent(context: Context) = Intent(context, HabitsActivity::class.java)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
//            navigator.applyCommand(Replace(Screens.HABITS, null))
        }
    }

    //    private val navigator = object : SupportAppNavigator(this,
//            supportFragmentManager, R.id.container) {
//
//        override fun createActivityIntent(screenKey: String?, data: Any?): Intent? =
//                when (screenKey) {
//                    Screens.CHECK_ACTIVITY ->
//                        CheckActivity.newIntent(this@HabitsActivity, data as String)
//                    else -> null
//                }
//
//        override fun createFragment(screenKey: String, data: Any?): Fragment? =
//                when (screenKey) {
//                    Screens.HABITS -> HabitsFragment.newInstance()
//                    else -> null
//                }
//    }
}