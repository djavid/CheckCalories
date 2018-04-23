package com.djavid.checksonline.view.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PointF
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseActivity
import com.djavid.checksonline.base.EmptyViewHolder
import com.djavid.checksonline.presenter.qrcode.QRCodePresenter
import com.djavid.checksonline.presenter.qrcode.QRCodeView
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_qrcode.*
import kotlinx.android.synthetic.main.layout_need_permission.*
import kotlinx.android.synthetic.main.qr_reader_layout.*
import toothpick.Toothpick
import javax.inject.Inject

class QRCodeActivity : BaseActivity(), QRCodeView, QRCodeReaderView.OnQRCodeReadListener {

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
    private var qrCodeReaderView: QRCodeReaderView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScopes(application, this))

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        emptyViewHolder = EmptyViewHolder(needPermissionLayout, { requestPermissions() })

        if (!isCameraPermissionGranted()) {
            requestPermissions()
        } else {
            initQrCodeReaderView()
        }
    }

    override fun onQRCodeRead(text: String, points: Array<PointF>) =
            presenter.onQrCodeRead(text, points)


    override fun onResume() {
        super.onResume()

        if (isCameraPermissionGranted()) {
            qrCodeReaderView?.startCamera()
        } else {
            qrCodeReaderView?.stopCamera()
            showEmptyView(true)
        }
    }

    override fun onPause() {
        super.onPause()

        qrCodeReaderView?.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) Toothpick.closeScope(this)
    }


    override fun initQrCodeReaderView() {

        val view = layoutInflater.inflate(R.layout.qr_reader_layout, qr_reader_view_frame)
        scan_btn.setOnClickListener({ presenter.onScanBtnClick() })
        showEmptyView(false)

        qrCodeReaderView = view.findViewById(R.id.qrdecoderview)
        qrCodeReaderView?.setOnQRCodeReadListener(this)

        // Use this function to enable/disable decoding
        qrCodeReaderView?.setQRDecodingEnabled(true)

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView?.setAutofocusInterval(2000L)

        // Use this function to enable/disable Torch
        qrCodeReaderView?.setTorchEnabled(true)

        // Use this function to set front camera preview
        qrCodeReaderView?.setFrontCamera()

        // Use this function to set back camera preview
        qrCodeReaderView?.setBackCamera()

        qrCodeReaderView?.startCamera()
    }

    override fun showEmptyView(show: Boolean) {
        if (show) emptyViewHolder?.showEmptyData()
        else emptyViewHolder?.hide()
    }

    override fun setDecodingEnabled(enabled: Boolean) {
        qrCodeReaderView?.setQRDecodingEnabled(enabled)
    }

    override fun stopCamera() = qrCodeReaderView!!.stopCamera()

    override fun startCamera() = qrCodeReaderView!!.startCamera()

    override fun requestPermissions() {
        val rxPermissions = RxPermissions(this)

        rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (granted) {
                        initQrCodeReaderView()
                    }
                }
    }

    private fun isCameraPermissionGranted() = ContextCompat
                .checkSelfPermission(applicationContext,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
}
