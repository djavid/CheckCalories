package com.djavid.checksonline.presenter.checks

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.Screens
import com.djavid.checksonline.base.BasePresenter
import com.djavid.checksonline.base.Paginator
import com.djavid.checksonline.interactors.ChecksInteractor
import com.djavid.checksonline.model.entities.DataPage
import com.djavid.checksonline.model.entities.Receipt
import org.joda.time.DateTime
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ChecksPresenter @Inject constructor(
        private val interactor: ChecksInteractor,
        router: Router
) : BasePresenter<ChecksView>(router) {

    private val checksFactory = { page: Int ->
        interactor.getChecks(page)
                .doOnSuccess {
                    if (it.result != null)
                        viewState.setToolbarSum(it.result.totalSum / 100f)
                }
                .map {

                    //if (it.result == null) return@map DataPage(it.result.receipts, false)
                    DataPage(it.result?.receipts?.sortedByDescending {
                        DateTime.parse(it.dateTime)
                    } ?: listOf(), it.result?.hasNext == true)
                }
    }
    private val checksController = ChecksController(viewState)
    private val checksPaginator = Paginator(checksFactory, checksController)


    override fun onFirstViewAttach() {
        refresh()
    }

    override fun attachView(view: ChecksView?) {
        super.attachView(view)
        //refresh()
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
        viewState.removeAllViews()
        viewState.noMoreToLoad()
        viewState.setLoadMoreResolver()
        checksPaginator.refresh()
    }

}