package com.djavid.checksonline.presenter.stats

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.base.BasePresenter
import com.djavid.checksonline.interactors.StatsInteractor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ShopsPresenter @Inject constructor(
        private val statsInteractor: StatsInteractor,
        router: Router
) : BasePresenter<ShopsView>(router)  {

    override fun onFirstViewAttach() {
        
    }
}