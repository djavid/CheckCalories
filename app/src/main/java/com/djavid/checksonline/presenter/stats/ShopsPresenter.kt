package com.djavid.checksonline.presenter.stats

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.base.BasePresenter
import com.djavid.checksonline.interactors.ChecksInteractor
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.toothpick.qualifiers.Title
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ShopsPresenter @Inject constructor(
        private val checksInteractor: ChecksInteractor,
        @Title private val title: String,
        router: Router
) : BasePresenter<ShopsView>(router)  {

    override fun onFirstViewAttach() {
        viewState.setToolbarTitle(title)
        refresh()
    }

    fun onCheckClicked(receipt: Receipt) {

    }

    fun refresh() {
        loadChecks()
    }

    private fun loadChecks() {
        checksInteractor.getChecksByShop(title, 0)
                .doOnSubscribe { viewState.showProgress(true) }
                .doAfterTerminate { viewState.showProgress(false) }
                .subscribe({
                    viewState.showChecks(it.result?.receipts ?: listOf())
                }, {
                    processError(it)
                })
    }
}