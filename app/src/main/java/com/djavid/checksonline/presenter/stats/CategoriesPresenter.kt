package com.djavid.checksonline.presenter.stats

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.base.BasePresenter
import com.djavid.checksonline.interactors.StatsInteractor
import com.djavid.checksonline.toothpick.qualifiers.Title
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class CategoriesPresenter @Inject constructor(
        private val statsInteractor: StatsInteractor,
        @Title private val title: String,
        router: Router
) : BasePresenter<CategoriesView>(router) {

    override fun onFirstViewAttach() {
        println(title)
        println("CategoriesPresenter")
    }
}