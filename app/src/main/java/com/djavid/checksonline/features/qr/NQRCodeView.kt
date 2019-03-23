package com.djavid.checksonline.features.qr

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.djavid.checksonline.R
import com.djavid.checksonline.features.common.EmptyViewHolder
import com.djavid.checksonline.features.root.ViewRoot
import com.djavid.checksonline.utils.playBeepSound
import com.djavid.checksonline.utils.vibrate
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_qrcode.view.*
import kotlinx.android.synthetic.main.layout_error_action.view.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import javax.inject.Inject

class NQRCodeView @Inject constructor(
        @ViewRoot private val viewRoot: View
) : QRContract.View {

    private lateinit var progressDialog: Dialog
    private lateinit var presenter: QRContract.Presenter

    private var stateDialog: Dialog? = null
    private var emptyViewHolder: EmptyViewHolder? = null
    private var mScannerView: ZXingScannerView? = null


    override fun init(presenter: QRContract.Presenter) {
        this.presenter = presenter
        initDialogs()

        emptyViewHolder = EmptyViewHolder(viewRoot.context.getString(R.string.scanner_permission_btn),
                viewRoot.context.getString(R.string.scanner_permission_warning),
                viewRoot.needPermissionLayout
        ) { requestPermissions() }

        if (!isCameraPermissionGranted()) {
            requestPermissions()
        } else {
            initZxingScannerView()
        }
    }

    @SuppressLint("CheckResult")
    override fun requestPermissions() {
        val rxPermissions = RxPermissions(viewRoot.context as QRActivity)
        rxPermissions //todo result disposable
                .request(Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (granted) {
                        initZxingScannerView()
                    }
                }
    }

    override fun onResume() {
        if (isCameraPermissionGranted()) {
            mScannerView?.setResultHandler(viewRoot.context as QRActivity)
            mScannerView?.startCamera()

            //old todo check this
            //if (successDialog.isShowing || failDialog.isShowing) stopScanning()
        } else {
            mScannerView?.stopCamera()
            showEmptyView(true)
        }
    }

    override fun onPause() {
        mScannerView?.stopCamera()

        if (progressDialog.isShowing) progressDialog.dismiss()
        if (stateDialog != null && stateDialog?.isShowing == true) stateDialog?.dismiss()
    }

    private fun initDialogs() {
        progressDialog = Dialog(viewRoot.context).apply {
            setContentView(R.layout.dialog_progress)
            setCancelable(false)
            setOnDismissListener { resumeScanning() }
        }
    }

    private fun initZxingScannerView() {
        showEmptyView(false)

        mScannerView = ZXingScannerView(viewRoot.context)
        mScannerView?.setAutoFocus(true)

        viewRoot.scanner_frame.addView(mScannerView)

        viewRoot.btn_torch.setOnClickListener {
            mScannerView?.flash = mScannerView?.flash?.not() ?: false
        }
    }

    private fun isCameraPermissionGranted() = ContextCompat.checkSelfPermission(viewRoot.context,
            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    override fun resumeScanning() {
        mScannerView?.resumeCameraPreview(viewRoot.context as QRActivity)
    }

    override fun stopScanning() {
        mScannerView?.stopCameraPreview()
    }

    override fun showEmptyView(show: Boolean) {
        emptyViewHolder?.show(show)
    }


    override fun showFailDialog() {
        stopScanning()
        stateDialog = Dialog(viewRoot.context).apply {
            setContentView(R.layout.dialog_fail)
            setCancelable(true)
            setOnDismissListener { resumeScanning() }

            window?.let {
                it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                it.attributes.dimAmount = 0.7f
            }

            findViewById<Button>(R.id.btn_back)
                    .setOnClickListener {
                        //presenter.onBackPressed() TODO
                    }
            findViewById<TextView>(R.id.btn_scan_more)
                    .setOnClickListener { dismiss() }
            show()
        }
    }

    override fun showSuccessDialog(receiptId: String) {
        stopScanning()
        stateDialog = Dialog(viewRoot.context).apply {

            setContentView(R.layout.dialog_success)
            setCancelable(true)
            setOnDismissListener { resumeScanning() }

            window?.let {
                it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                it.attributes.dimAmount = 0.7f
            }

            findViewById<Button>(R.id.btn_open)
                    .setOnClickListener { presenter.onOpenButtonClicked(receiptId) }
            findViewById<TextView>(R.id.btn_scan_more)
                    .setOnClickListener { dismiss() }
            show()
        }
    }

    override fun showWaitDialog() {
        stopScanning()
        stateDialog = Dialog(viewRoot.context).apply {
            setContentView(R.layout.dialog_wait)
            setCancelable(true)
            setOnDismissListener { resumeScanning() }

            window?.let {
                it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                it.attributes.dimAmount = 0.7f
            }

            findViewById<Button>(R.id.btn_back)
                    .setOnClickListener {
                        //presenter.onBackPressed() todo
                    }
            findViewById<TextView>(R.id.btn_scan_more)
                    .setOnClickListener { dismiss() }
            show()
        }
    }

    override fun playBeep() = playBeepSound()

    override fun vibrate() = viewRoot.context.vibrate()

    override fun showProgress(show: Boolean) {
        if (show) {
            stopScanning()
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }
    }

}