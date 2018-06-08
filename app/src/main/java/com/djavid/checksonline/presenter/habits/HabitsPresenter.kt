package com.djavid.checksonline.presenter.habits

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.Screens
import com.djavid.checksonline.base.BasePresenter
import com.djavid.checksonline.interactors.StatsInteractor
import com.djavid.checksonline.model.entities.Receipt
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class HabitsPresenter @Inject constructor(
        private val interactor: StatsInteractor,
        router: Router
) : BasePresenter<HabitsView>(router) {

    override fun onFirstViewAttach() {
        getHabits()
    }

    private fun getHabits() {
        interactor.getHabits()
                .doOnSubscribe { viewState.showProgress(true) }
                .doAfterTerminate { viewState.showProgress(false) }
                .subscribe({
                    if (it.error.isEmpty()) {
                        viewState.showHabits(it.result)
                    }
                }, {
                    processError(it)
                })

    }

    fun onCheckClicked(receipt: Receipt) {
        router.navigateTo(Screens.CHECK_ACTIVITY, receipt.receiptId.toString())
    }

}