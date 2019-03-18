package com.djavid.checksonline.features.QRCode

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.widget.Button
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.djavid.checksonline.R
import com.djavid.checksonline.Screens
import com.djavid.checksonline.features.base.BaseActivity
import com.djavid.checksonline.features.check.CheckActivity
import com.djavid.checksonline.features.common.EmptyViewHolder
import com.djavid.checksonline.features.receipt_input.ReceiptInputActivity
import com.djavid.checksonline.utils.playBeepSound
import com.djavid.checksonline.utils.vibrate
import com.google.zxing.Result
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_qrcode.*
import kotlinx.android.synthetic.main.layout_error_action.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject


class QRCodeActivity : BaseActivity(), QRCodeView, ZXingScannerView.ResultHandler {

    companion object {
        fun newIntent(ctx: Context) = Intent(ctx, QRCodeActivity::class.java)
    }

    @Inject
    lateinit var holder: NavigatorHolder

    @InjectPresenter
    lateinit var presenter: QRCodePresenter

    @ProvidePresenter
    fun providePresenter(): QRCodePresenter =
            Toothpick.openScopes(application, this)
                    .getInstance(QRCodePresenter::class.java)

    private var emptyViewHolder: EmptyViewHolder? = null
    private var mScannerView: ZXingScannerView? = null
    private lateinit var progressDialog: Dialog
    private var stateDialog: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScopes(application, this))

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)
        initDialogs()

        emptyViewHolder = EmptyViewHolder(
                getString(R.string.scanner_permission_btn),
                getString(R.string.scanner_permission_warning),
                needPermissionLayout, { requestPermissions() })

        if (!isCameraPermissionGranted()) {
            requestPermissions()
        } else {
            initZxingScannerView()
        }

        btn_manual_add.setOnClickListener { presenter.onManualInputBtnClick() }
    }

    override fun onResume() {
        super.onResume()

        if (isCameraPermissionGranted()) {
            mScannerView?.setResultHandler(this)
            mScannerView?.startCamera()

            //todo check this
            //if (successDialog.isShowing || failDialog.isShowing) stopScanning()
        } else {
            mScannerView?.stopCamera()
            showEmptyView(true)
        }
    }

    override fun onPause() {
        super.onPause()
        holder.removeNavigator()
        mScannerView?.stopCamera()

        if (progressDialog.isShowing) progressDialog.dismiss()
        if (stateDialog != null && stateDialog?.isShowing == true) stateDialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) Toothpick.closeScope(this)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        holder.setNavigator(navigator)
    }


    private fun initDialogs() {
        progressDialog = Dialog(this).apply {
            setContentView(R.layout.dialog_progress)
            setCancelable(false)
            setOnDismissListener { resumeScanning() }
        }
    }

    private fun initZxingScannerView() {
        showEmptyView(false)

        mScannerView = ZXingScannerView(this)
        mScannerView?.setAutoFocus(true)

        scanner_frame.addView(mScannerView)

        btn_torch.setOnClickListener {
            mScannerView?.flash = mScannerView?.flash?.not() ?: false
        }
    }

    override fun resumeScanning() {
        mScannerView?.resumeCameraPreview(this)
    }

    override fun stopScanning() {
        mScannerView?.stopCameraPreview()
    }

    override fun showEmptyView(show: Boolean) {
        emptyViewHolder?.show(show)
    }

    override fun handleResult(rawResult: Result) = presenter.onQrCodeRead(rawResult.text)

    override fun requestPermissions() {
        val rxPermissions = RxPermissions(this)
        rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (granted) {
                        initZxingScannerView()
                    }
                }
    }

    private fun isCameraPermissionGranted() = ContextCompat
            .checkSelfPermission(applicationContext,
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED


    override fun showFailDialog() {
        stopScanning()
        stateDialog = Dialog(this).apply {
            setContentView(R.layout.dialog_fail)
            setCancelable(true)
            setOnDismissListener { resumeScanning() }

            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.attributes.dimAmount = 0.7f

            findViewById<Button>(R.id.btn_back)
                    .setOnClickListener { presenter.onBackPressed() }
            findViewById<TextView>(R.id.btn_scan_more)
                    .setOnClickListener { dismiss() }
            show()
        }
    }

    override fun showSuccessDialog(receiptId: String) {
        stopScanning()
        stateDialog = Dialog(this).apply {

            setContentView(R.layout.dialog_success)
            setCancelable(true)
            setOnDismissListener { resumeScanning() }

            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.attributes.dimAmount = 0.7f

            findViewById<Button>(R.id.btn_open)
                    .setOnClickListener { presenter.onOpenButtonClicked(receiptId) }
            findViewById<TextView>(R.id.btn_scan_more)
                    .setOnClickListener { dismiss() }
            show()
        }
    }

    override fun showWaitDialog() {
        stopScanning()
        stateDialog = Dialog(this).apply {
            setContentView(R.layout.dialog_wait)
            setCancelable(true)
            setOnDismissListener { resumeScanning() }

            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.attributes.dimAmount = 0.7f

            findViewById<Button>(R.id.btn_back)
                    .setOnClickListener { presenter.onBackPressed() }
            findViewById<TextView>(R.id.btn_scan_more)
                    .setOnClickListener { dismiss() }
            show()
        }
    }


    override fun playBeep() = playBeepSound()

    override fun vibrate() = applicationContext.vibrate()

    override fun showProgress(show: Boolean) {
        if (show) {
            stopScanning()
            progressDialog.show()
        }
        else {
            progressDialog.dismiss()
        }
    }


    private val navigator = object : SupportAppNavigator(this, R.id.container) {

        override fun createActivityIntent(screenKey: String?, data: Any?): Intent? =
                when (screenKey) {
                    Screens.CHECK_ACTIVITY ->
                        CheckActivity.newIntent(this@QRCodeActivity, data as String)
                    Screens.RECEIPT_INPUT_ACTIVITY ->
                        ReceiptInputActivity.newIntent(this@QRCodeActivity)
                    else -> null
                }

        override fun createFragment(screenKey: String, data: Any?): Fragment? = null

        override fun unknownScreen(command: Command) {
            throw IllegalArgumentException("Wrong command: $command")
        }
    }

}
