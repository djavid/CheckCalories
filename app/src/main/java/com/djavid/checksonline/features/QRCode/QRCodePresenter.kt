package com.djavid.checksonline.features.QRCode

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.Screens
import com.djavid.checksonline.features.base.BasePresenter
import com.djavid.checksonline.interactors.QrCodeInteractor
import com.djavid.checksonline.model.networking.bodies.FnsValues
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

            val fnsValues = FnsValues(m.group(1), m.group(2), m.group(3),
                    m.group(4), m.group(5))
            sendCheck(fnsValues)
        }
    }

    fun onScanBtnClick() {
        if (!inProcess) {
            viewState.resumeScanning()
        }
    }

    fun onManualInputBtnClick() {
        router.navigateTo(Screens.RECEIPT_INPUT_ACTIVITY)
    }

    fun onOpenButtonClicked(receiptId: String) {
        router.navigateTo(Screens.CHECK_ACTIVITY, receiptId)
    }

    private fun sendCheck(fnsValues: FnsValues) {

        interactor.sendCheck(fnsValues)
                .doOnSubscribe {
                    unsubscribeOnDestroy(it)
                    setProgress(true)
                }
                .doAfterTerminate { setProgress(false) }
                .subscribe({
                    if (!it.error.isEmpty()) {
                        when (it.error) {
                            "Check not found." -> viewState.showFailDialog()
                            "Check has not loaded yet." -> viewState.showWaitDialog()
                            "Check already exists." -> viewState.showToastyWarning("Чек уже был добавлен!")
                            else -> viewState.showToastyError("Произошла ошибка!")
                        }

                    } else if (it.result != null) {
                        viewState.showSuccessDialog(it.result.receiptId.toString())
                    }

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