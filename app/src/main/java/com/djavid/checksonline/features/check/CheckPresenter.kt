package com.djavid.checksonline.features.check

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.dagger.qualifiers.CheckId
import com.djavid.checksonline.features.base.BasePresenter
import com.djavid.checksonline.interactors.ChecksInteractor
import com.djavid.checksonline.model.entities.Receipt
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class CheckPresenter @Inject constructor(
        private val interactor: ChecksInteractor,
        @CheckId private val checkId: String,
        router: Router
) : BasePresenter<CheckView>(router) {

    override fun onFirstViewAttach() {
        getCheck()
    }

    @SuppressLint("CheckResult")
    private fun getCheck() {
        interactor.getCheck(checkId.toLong())
                .doOnSubscribe{
                    unsubscribeOnDestroy(it)
                    viewState.showProgress(true)
                }
                .doAfterTerminate { viewState.showProgress(false) }
                .subscribe({
                    onCheckReceived(it)
                }, {
                    processError(it)
                })
    }

    private fun onCheckReceived(receipt: Receipt) {
        viewState.setGoods(receipt.items)
        viewState.setToolbar(receipt)
    }

}