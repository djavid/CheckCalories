package com.djavid.checksonline.features.receipt_input

import com.djavid.checksonline.features.app.Screens
import com.djavid.checksonline.interactors.QrCodeInteractor
import com.djavid.checksonline.model.networking.bodies.FnsValues
import io.reactivex.disposables.Disposable
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class ReceiptPresenter @Inject constructor(
        private val view: ReceiptContract.View,
        private val interactor: QrCodeInteractor,
        private val router: Router
) : ReceiptContract.Presenter {

    private var disposable: Disposable? = null

    override fun init() {
        view.init(this)
    }

    override fun onOpenButtonClicked(receiptId: String) {
        router.navigateTo(Screens.CHECK_ACTIVITY, receiptId)
    }

    override fun sendCheck(fnsValues: FnsValues) {
        disposable = interactor.sendCheck(fnsValues)
                .doOnSubscribe {
                    //TODO unsubscribeOnDestroy(it)
                    view.showProgress(true)
                }
                .doAfterTerminate { view.showProgress(false) }
                .subscribe({
                    if (!it.error.isEmpty()) {
                        when (it.error) {
                            "Check not found." -> view.showFailDialog()
                            "Check has not loaded yet." -> view.showWaitDialog()
                            "Check already exists." -> {
                            } // TODO view.showToastyWarning("Чек уже был добавлен!")
                            else -> {
                            } // TODO view.showToastyError("Произошла ошибка!")
                        }

                    } else if (it.result != null) {
                        view.showSuccessDialog(it.result.receiptId.toString())
                    }

                }, {
                    // TODO processError(it)
                    view.showFailDialog()
                })
    }

    override fun onDestroy() {
        disposable?.dispose()
    }

    override fun onBackPressed() {
    }
}