package com.djavid.checksonline.presenter.habits

import com.djavid.checksonline.base.BasePresenter
import com.djavid.checksonline.interactors.StatsInteractor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class HabitsPresenter @Inject constructor(
        private val interactor: StatsInteractor,
        router: Router
) : BasePresenter<HabitsView>(router) {

    override fun onFirstViewAttach() {

    }

}