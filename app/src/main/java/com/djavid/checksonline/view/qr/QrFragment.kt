package com.djavid.checksonline.view.qr

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseFragment
import com.djavid.checksonline.contracts.qr.QrContract
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class QrFragment : BaseFragment(), ZXingScannerView.ResultHandler {

    override val layoutResId = R.layout.activity_qrcode
    
    lateinit var presenter: QrContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init()
    }

    override fun handleResult(rawResult: Result) = presenter.onQrCodeRead(rawResult.text)

}