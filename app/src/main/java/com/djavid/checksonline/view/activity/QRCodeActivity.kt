package com.djavid.checksonline.view.activity

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
import com.djavid.checksonline.base.BaseActivity
import com.djavid.checksonline.base.EmptyViewHolder
import com.djavid.checksonline.presenter.qrcode.QRCodePresenter
import com.djavid.checksonline.presenter.qrcode.QRCodeView
import com.google.zxing.Result
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_qrcode.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import toothpick.Toothpick
import com.djavid.checksonline.utils.playBeepSound
import com.djavid.checksonline.utils.vibrate
import kotlinx.android.synthetic.main.layout_need_permission.*
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
    private var successDialog: Dialog? = null
    private var failDialog: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScopes(application, this))

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)
        initDialogs()

        emptyViewHolder = EmptyViewHolder(needPermissionLayout, { requestPermissions() })

        if (!isCameraPermissionGranted()) {
            requestPermissions()
        } else {
            initZxingScannerView()
        }
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
        if (failDialog != null && failDialog?.isShowing == true) failDialog?.dismiss()
        if (successDialog != null && successDialog?.isShowing == true) successDialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) Toothpick.closeScope(this)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        holder.setNavigator(navigator)
    }

    override fun showMessage(message: String) {

    }


    private fun initDialogs() {
        progressDialog = Dialog(this).apply {
            setContentView(R.layout.dialog_progress)
            setCancelable(false)
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
        if (show) emptyViewHolder?.showEmptyData()
        else emptyViewHolder?.hide()
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


    override fun showFailDialog() {
        stopScanning()
        failDialog = Dialog(this).apply {
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
        //stopScanning()
        successDialog = Dialog(this).apply {

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


    private fun isCameraPermissionGranted() = ContextCompat
            .checkSelfPermission(applicationContext,
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    override fun playBeep() = playBeepSound()

    override fun vibrate() = applicationContext.vibrate()

    override fun showProgress(show: Boolean) {
        if (show) progressDialog.show()
        else progressDialog.dismiss()
    }


    private val navigator = object : SupportAppNavigator(this, R.id.container) {

        override fun createActivityIntent(screenKey: String?, data: Any?): Intent? =
                when (screenKey) {
                    Screens.CHECK_ACTIVITY ->
                        CheckActivity.newIntent(this@QRCodeActivity, data as String)
                    else -> null
                }

        override fun createFragment(screenKey: String, data: Any?): Fragment? = null

        override fun unknownScreen(command: Command) {
            throw IllegalArgumentException("Wrong command: $command")
        }
    }

}
