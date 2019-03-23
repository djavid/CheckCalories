package com.djavid.checksonline.features.qr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.djavid.checksonline.R
import com.djavid.checksonline.Screens
import com.djavid.checksonline.features.base.NewBaseActivity
import com.djavid.checksonline.features.check.CheckActivity
import com.djavid.checksonline.features.receipt_input.ReceiptInputActivity
import com.google.zxing.Result
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_qrcode.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject

class QrActivity : NewBaseActivity(), ZXingScannerView.ResultHandler {

    companion object {
        fun newIntent(ctx: Context) = Intent(ctx, QrActivity::class.java)
    }

    @Inject
    lateinit var holder: NavigatorHolder

    @Inject
    lateinit var presenter: QrContract.Presenter

    private var disposable: Disposable? = null
    override val layoutResId: Int get() = R.layout.activity_qrcode


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_manual_add.setOnClickListener { presenter.onManualInputBtnClick() }
    }

    override fun onPause() {
        super.onPause()
        holder.removeNavigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        holder.setNavigator(navigator)
    }

    override fun handleResult(rawResult: Result) = presenter.onQrCodeRead(rawResult.text)


    private val navigator = object : SupportAppNavigator(this, R.id.container) {

        override fun createActivityIntent(screenKey: String?, data: Any?): Intent? =
                when (screenKey) {
                    Screens.CHECK_ACTIVITY ->
                        CheckActivity.newIntent(this@QrActivity, data as String)
                    Screens.RECEIPT_INPUT_ACTIVITY ->
                        ReceiptInputActivity.newIntent(this@QrActivity)
                    else -> null
                }

        override fun createFragment(screenKey: String, data: Any?): Fragment? = null

        override fun unknownScreen(command: Command) {
            throw IllegalArgumentException("Wrong command: $command")
        }
    }

}
