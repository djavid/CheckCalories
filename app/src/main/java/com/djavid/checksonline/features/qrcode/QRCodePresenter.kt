package com.djavid.checksonline.features.qrcode

import com.djavid.checksonline.Screens
import com.djavid.checksonline.interactors.QrCodeInteractor
import com.djavid.checksonline.model.networking.bodies.FnsValues
import com.djavid.checksonline.utils.getCheckMatcher
import io.reactivex.disposables.Disposable
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class QRCodePresenter @Inject constructor(
        private val view: QRContract.View,
        private val interactor: QrCodeInteractor,
        private val router: Router
) : QRContract.Presenter() {

    private var disposable: Disposable? = null

    var inProcess: Boolean = false


    override fun init() {
        view.init(this)
    }


    override fun onQrCodeRead(text: String) {
        val m = text.getCheckMatcher()

        if (m.matches()) {
            view.vibrate()

            val fnsValues = FnsValues(m.group(1), m.group(2), m.group(3),
                    m.group(4), m.group(5))
            sendCheck(fnsValues)
        }
    }

    override fun onScanBtnClick() {
        if (!inProcess) {
            view.resumeScanning()
        }
    }

    override fun onManualInputBtnClick() {
        router.navigateTo(Screens.RECEIPT_INPUT_ACTIVITY)
    }

    override fun onOpenButtonClicked(receiptId: String) {
        router.navigateTo(Screens.CHECK_ACTIVITY, receiptId)
    }

    private fun sendCheck(fnsValues: FnsValues) {

        disposable = interactor.sendCheck(fnsValues)
                .doOnSubscribe {
                    unsubscribeOnDestroy(it)
                    setProgress(true)
                }
                .doAfterTerminate { setProgress(false) }
                .subscribe({
                    if (!it.error.isEmpty()) {
                        when (it.error) {
                            "Check not found." -> view.showFailDialog()
                            "Check has not loaded yet." -> view.showWaitDialog()
                            "Check already exists." -> {
                            } //view.showToastyWarning("Чек уже был добавлен!") todo
                            else -> {
                            } // view.showToastyError("Произошла ошибка!") todo
                        }

                    } else if (it.result != null) {
                        view.showSuccessDialog(it.result.receiptId.toString())
                    }

                }, {
                    //TODO processError(it)
                    view.showFailDialog()
                })
    }

    private fun setProgress(progress: Boolean) {
        inProcess = progress
        // todo view.showProgress(progress)
    }

}