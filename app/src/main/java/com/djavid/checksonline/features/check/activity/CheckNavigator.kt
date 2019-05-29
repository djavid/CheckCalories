package com.djavid.checksonline.features.check.activity

import android.content.Context
import android.content.Intent
import com.djavid.checksonline.features.root.OriginActivityContext
import javax.inject.Inject

class CheckNavigator @Inject constructor(
        @OriginActivityContext val context: Context
) : CheckActivityContract.Navigator {

    override fun goToCheckScreen() {
        val intent = Intent(context, CheckActivity::class.java).apply {
            putExtra(EXTRA_CHECK_ID, "id")
        }
        context.startActivity(intent)
    }

}