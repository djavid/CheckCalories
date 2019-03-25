package com.djavid.checksonline.features.qr

import android.content.Context
import android.content.Intent
import com.djavid.checksonline.features.root.OriginActivityContext
import javax.inject.Inject

class QrNavigator @Inject constructor(
        @OriginActivityContext private val context: Context
) : QrContract.Navigator {

    override fun goToQrScreen() {
        val intent = Intent(context, QrActivity::class.java)
        context.startActivity(intent)
    }
}