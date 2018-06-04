package com.djavid.checksonline.presenter.stats

import com.arellomobile.mvp.InjectViewState
import com.djavid.checksonline.base.BasePresenter
import com.djavid.checksonline.interactors.ChecksInteractor
import com.djavid.checksonline.toothpick.qualifiers.Title
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class CategoriesPresenter @Inject constructor(
        private val interactor: ChecksInteractor,
        @Title private val title: String,
        router: Router
) : BasePresenter<CategoriesView>(router) {

    private var currentPage: Int = 0
    private var hasNext: Boolean = false


    override fun onFirstViewAttach() {
        viewState.setToolbarTitle(title)
        refresh()
    }

    fun loadMoreChecks() {
        if (hasNext) loadItems(currentPage + 1, false)
        else viewState.noMoreToLoad()
    }

    fun refresh() {
        loadItems(0, true)
    }

    private fun loadItems(page: Int, show: Boolean) {
        currentPage = page

        interactor.getItemsByCategory(title, page)
                .doOnSubscribe { if (show) viewState.showProgress(true) }
                .doAfterTerminate { if (show) viewState.showProgress(false) }
                .subscribe({
                    hasNext = it.result?.hasNext ?: false
                    viewState.showItems(it.result?.items ?: listOf(), page == 0)
                }, {
                    processError(it)
                })
    }
}