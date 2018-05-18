package com.djavid.checksonline.presenter.qrcode

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.Screens
import com.djavid.checksonline.base.BasePresenter
import com.djavid.checksonline.interactors.QrCodeInteractor
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.utils.getCheckMatcher
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class QRCodePresenter @Inject constructor(
        private val interactor: QrCodeInteractor,
        router: Router
) : BasePresenter<QRCodeView>(router) {

    var inProcess: Boolean = false


    override fun onFirstViewAttach() {

    }

    fun onQrCodeRead(text: String) {
        val m = text.getCheckMatcher()

        if (m.matches()) {
            viewState.vibrate()

            loadAndSaveCheck(m.group(3), m.group(4), m.group(5), false)
        }
    }

    fun onScanBtnClick() {
        if (!inProcess) {
            viewState.resumeScanning()
        }
    }

    fun onOpenButtonClicked(receiptId: String) {
        router.navigateTo(Screens.CHECK_ACTIVITY, receiptId)
    }

    private fun loadAndSaveCheck(fiscalDriveNumber: String, fiscalDocumentNumber: String,
                                 fiscalSign: String, sendToEmail: Boolean) {
        interactor.getCheck(fiscalDriveNumber, fiscalDocumentNumber, fiscalSign, sendToEmail)
                .doOnSubscribe {
                    unsubscribeOnDestroy(it)
                    setProgress(true)
                }
                .flatMap { interactor.sendCheck(it.document.receipt) }
                .doAfterTerminate { setProgress(false) }
                .subscribe({
                    viewState.showSuccessDialog(it.result.receiptId.toString())
                }, {
                    processError(it)
                    viewState.showFailDialog()
                })
    }

    private fun setProgress(progress: Boolean) {
        inProcess = progress
        viewState.showProgress(progress)
    }

}