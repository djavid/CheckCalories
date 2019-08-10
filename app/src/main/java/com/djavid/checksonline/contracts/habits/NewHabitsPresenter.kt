package com.djavid.checksonline.contracts.habits

import android.annotation.SuppressLint
import com.djavid.checksonline.base.BaseActivity
import com.djavid.checksonline.interactors.StatsInteractor
import com.djavid.checksonline.model.entities.Receipt

class NewHabitsPresenter constructor(
        private val view: HabitsContract.View,
        private val interactor: StatsInteractor
): HabitsContract.Presenter {

    override fun init() {
        view.init(this)
    }

    @SuppressLint("CheckResult")
    override fun getHabits() {
        interactor.getHabits()
                .doOnSubscribe { (view as BaseActivity).showProgress(true) }
                .doAfterTerminate { (view as BaseActivity).showProgress(false) }
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