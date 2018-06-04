package com.djavid.checksonline.presenter.stats

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.Screens
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

    private var currentPage: Int = 0
    private var hasNext: Boolean = false


    override fun onFirstViewAttach() {
        viewState.setToolbarTitle(title)
        refresh()
    }

    fun onCheckClicked(receipt: Receipt) {
        router.navigateTo(Screens.CHECK_ACTIVITY, receipt.receiptId.toString())
    }

    fun loadMoreChecks() {
        if (hasNext) loadChecks(currentPage + 1, false)
        else viewState.noMoreToLoad()
    }

    fun refresh() {
        loadChecks(0, true)
    }

    private fun loadChecks(page: Int, show: Boolean) {
        currentPage = page

        checksInteractor.getChecksByShop(title, page)
                .doOnSubscribe { if (show) viewState.showProgress(true) }
                .doAfterTerminate { if (show) viewState.showProgress(false) }
                .subscribe({
                    hasNext = it.result?.hasNext ?: false
                    viewState.showChecks(it.result?.receipts ?: listOf(), page == 0)
                }, {
                    processError(it)
                })
    }

}