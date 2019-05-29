package com.djavid.checksonline.features.receipt_input

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.djavid.checksonline.R
import com.djavid.checksonline.features.base.NewBaseActivity

class ReceiptInputActivity : NewBaseActivity() {

    companion object {
        fun newIntent(context: Context) = Intent(context, ReceiptInputActivity::class.java)
    }

//    @Inject
//    lateinit var holder: NavigatorHolder

    override val layoutResId: Int get() = R.layout.activity_receipt_input

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
//            navigator.applyCommand(Replace(Screens.RECEIPT_INPUT, null))
        }
    }

    //    private val navigator = object : SupportAppNavigator(this,
//            supportFragmentManager, R.id.container) {
//
//        override fun createActivityIntent(screenKey: String?, data: Any?): Intent? =
//                when (screenKey) {
//                    Screens.CHECK_ACTIVITY ->
//                        CheckActivity.newIntent(this@ReceiptInputActivity, data as String)
//                    else -> null
//                }
//
//        override fun createFragment(screenKey: String, data: Any?): Fragment? =
//                when (screenKey) {
//                    Screens.RECEIPT_INPUT -> ReceiptInputFragment.newInstance()
//                    else -> null
//                }
//    }
}
