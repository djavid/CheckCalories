package com.djavid.checksonline.presenter.check

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.base.BasePresenter
import com.djavid.checksonline.interactors.ChecksInteractor
import com.djavid.checksonline.toothpick.qualifiers.CheckId
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class CheckPresenter @Inject constructor(
        private val interactor: ChecksInteractor,
        @CheckId private val checkId: String,
        router: Router
) : BasePresenter<CheckView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        interactor.getCheck(checkId.toLong())
                .subscribe({
                    viewState.setGoods(it.items)
                    viewState.setToolbarSum(it.totalSum)
                    viewState.setToolbarAddress(it.retailPlaceAddress)
                    viewState.setDatetime(it.dateTime)
                }, {
                    it.printStackTrace()
                    viewState.showMessage(it.localizedMessage)
                })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}