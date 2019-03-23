package com.djavid.checksonline.features.check.fragment

import com.djavid.checksonline.interactors.ChecksInteractor
import com.djavid.checksonline.model.entities.Receipt
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CheckPresenter @Inject constructor(
        private val view: CheckFragmentContract.View,
        private val interactor: ChecksInteractor
) : CheckFragmentContract.Presenter {

    private var disposable: Disposable? = null

    override fun init(checkId: String) {
        getCheck(checkId)
    }

    private fun getCheck(checkId: String) {
        disposable = interactor.getCheck(checkId.toLong())
                .doOnSubscribe { view.showProgress(true) }
                .doAfterTerminate { view.showProgress(false) }
                .subscribe({
                    onCheckReceived(it)
                }, {
                    //TODO processError(it)
                })
    }

    private fun onCheckReceived(receipt: Receipt) {
        view.setGoods(receipt.items)
        view.setToolbar(receipt)
    }

    fun onDestroy() {
        disposable?.dispose()
    }

}