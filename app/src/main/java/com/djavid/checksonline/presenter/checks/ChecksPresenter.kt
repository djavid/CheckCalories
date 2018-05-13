package com.djavid.checksonline.presenter.checks

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.Screens
import com.djavid.checksonline.base.BasePresenter
import com.djavid.checksonline.base.Paginator
import com.djavid.checksonline.interactors.ChecksInteractor
import com.djavid.checksonline.model.entities.Receipt
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ChecksPresenter @Inject constructor(
        private val interactor: ChecksInteractor,
        router: Router
) : BasePresenter<ChecksView>(router) {

    private val checksFactory = { page: Int ->
        interactor.getChecks(1) //page
    }
    private val checksController = ChecksController(viewState)
    private val checksPaginator = Paginator(checksFactory, checksController)


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        checksPaginator.release()
    }

    fun onFabClicked() {
        router.navigateTo(Screens.QR_CODE)
    }

    fun onCheckClicked(receipt: Receipt) {
        router.navigateTo(Screens.CHECK_ACTIVITY, receipt.receiptId.toString())
    }

    fun loadMoreChecks() { //TODO
        checksPaginator.loadNewPage()
    }

    fun refresh() {
        checksPaginator.refresh()
    }

}