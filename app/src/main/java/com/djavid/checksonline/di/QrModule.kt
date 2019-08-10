package com.djavid.checksonline.di

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.djavid.checksonline.contracts.qr.QrContract
import com.djavid.checksonline.contracts.qr.QrPresenter
import com.djavid.checksonline.utils.MODULE_QR
import com.djavid.checksonline.view.qr.QrView
import kotlinx.android.synthetic.main.activity_qrcode.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class QrModule(fragment: Fragment) {
    val kodein = Kodein.Module(MODULE_QR) {
        bind<View>() with singleton { fragment.qr_container }
        bind<Fragment>() with singleton { fragment }
        bind<Lifecycle>() with singleton { fragment.lifecycle }
        bind<QrContract.Presenter>() with singleton { QrPresenter(instance(), instance(), instance()) }
        bind<QrContract.View>() with singleton { QrView(instance(), instance(), instance()) }
    }
}