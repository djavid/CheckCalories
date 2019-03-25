package com.djavid.checksonline.features.habits

import android.annotation.SuppressLint
import com.djavid.checksonline.features.base.NewBaseActivity
import com.djavid.checksonline.interactors.StatsInteractor
import com.djavid.checksonline.model.entities.Receipt
import javax.inject.Inject

class NewHabitsPresenter @Inject constructor(
        private val view: HabitsContract.View,
        private val interactor: StatsInteractor
): HabitsContract.Presenter {

    override fun init() {
        view.init(this)
    }

    @SuppressLint("CheckResult")
    override fun getHabits() {
        interactor.getHabits()
                .doOnSubscribe { (view as NewBaseActivity).showProgress(true) }
                .doAfterTerminate { (view as NewBaseActivity).showProgress(false) }
                .subscribe({
                    if (it.error.isEmpty()) {
                        view.showHabits(it.result)
                    }
                }, {
                    //processError(it)
                })
    }

    override fun onCheckClicked(receipt: Receipt) {
    }
}