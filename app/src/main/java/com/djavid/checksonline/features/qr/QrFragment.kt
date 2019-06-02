package com.djavid.checksonline.features.qr

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.features.base.NewBaseFragment
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import javax.inject.Inject

class QrFragment : NewBaseFragment(), ZXingScannerView.ResultHandler {

    override val layoutResId = R.layout.activity_qrcode

    @Inject
    lateinit var presenter: QrContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init()
    }

    override fun handleResult(rawResult: Result) = presenter.onQrCodeRead(rawResult.text)

}