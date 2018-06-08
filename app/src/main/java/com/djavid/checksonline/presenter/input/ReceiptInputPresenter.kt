package com.djavid.checksonline.presenter.input

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.Screens
import com.djavid.checksonline.base.BasePresenter
import com.djavid.checksonline.interactors.QrCodeInteractor
import com.djavid.checksonline.model.networking.bodies.FnsValues
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ReceiptInputPresenter @Inject constructor(
        private val interactor: QrCodeInteractor,
        router: Router
) : BasePresenter<ReceiptInputView>(router) {

    override fun onFirstViewAttach() {

    }

    fun onOpenButtonClicked(receiptId: String) {
        router.navigateTo(Screens.CHECK_ACTIVITY, receiptId)
    }

    fun sendCheck(fnsValues: FnsValues) {

        interactor.sendCheck(fnsValues)
                .doOnSubscribe {
                    unsubscribeOnDestroy(it)
                    viewState.showProgress(true)
                }
                .doAfterTerminate { viewState.showProgress(false) }
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

}