package com.djavid.checksonline.view.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseActivity
import com.djavid.checksonline.base.EmptyViewHolder
import com.djavid.checksonline.presenter.qrcode.QRCodePresenter
import com.djavid.checksonline.presenter.qrcode.QRCodeView
import com.google.zxing.Result
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_qrcode.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import toothpick.Toothpick
import android.media.ToneGenerator
import android.media.AudioManager
import android.os.VibrationEffect
import android.os.Build
import android.os.Vibrator
import com.djavid.checksonline.utils.playBeepSound
import com.djavid.checksonline.utils.vibrate
import kotlinx.android.synthetic.main.layout_need_permission.*

class QRCodeActivity : BaseActivity(), QRCodeView, ZXingScannerView.ResultHandler {

    companion object {
        fun newIntent(ctx: Context) = Intent(ctx, QRCodeActivity::class.java)
    }

    @InjectPresenter
    lateinit var presenter: QRCodePresenter

    @ProvidePresenter
    fun providePresenter(): QRCodePresenter =
            Toothpick.openScopes(application, this)
                    .getInstance(QRCodePresenter::class.java)

    private var emptyViewHolder: EmptyViewHolder? = null
    private lateinit var mScannerView: ZXingScannerView


    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScopes(application, this))

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

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
            mScannerView.setResultHandler(this)
            mScannerView.startCamera()
        } else {
            mScannerView.stopCamera()
            showEmptyView(true)
        }
    }

    override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) Toothpick.closeScope(this)
    }


    private fun initZxingScannerView() {
        mScannerView = ZXingScannerView(this)
        mScannerView.setAutoFocus(true)

        scanner_frame.addView(mScannerView)
        scan_btn.setOnClickListener{ presenter.onScanBtnClick() }
    }

    override fun resumeScanning() = mScannerView.resumeCameraPreview(this)

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


    private fun isCameraPermissionGranted() = ContextCompat
            .checkSelfPermission(applicationContext,
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    override fun playBeep() = playBeepSound()

    override fun vibrate() = applicationContext.vibrate()
}
