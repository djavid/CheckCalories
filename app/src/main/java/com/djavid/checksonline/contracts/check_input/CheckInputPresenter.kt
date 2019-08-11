package com.djavid.checksonline.contracts.check_input

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.djavid.checksonline.interactors.QrCodeInteractor
import com.djavid.checksonline.model.networking.bodies.FnsValues
import io.reactivex.disposables.Disposable

class CheckInputPresenter constructor(
		private val view: CheckInputContract.View,
		private val interactor: QrCodeInteractor,
		private val lifecycle: Lifecycle
) : CheckInputContract.Presenter, LifecycleObserver {

    private var disposable: Disposable? = null

    override fun init() {
        view.init(this)
		lifecycle.addObserver(this)
    }

    override fun onOpenButtonClicked(receiptId: String) {
//        router.navigateTo(Screens.CHECK_ACTIVITY, receiptId)
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
	
	@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
	fun onDestroy() {
        disposable?.dispose()
    }

    override fun onBackPressed() {
		//todo go to checks screen
    }
	
}