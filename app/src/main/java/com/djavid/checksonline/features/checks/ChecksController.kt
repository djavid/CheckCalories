package com.djavid.checksonline.features.checks

import com.djavid.checksonline.features.common.Paginator
import com.djavid.checksonline.model.entities.Receipt

class ChecksController(private val view: ChecksView) : Paginator.ViewController<Receipt> {

    override fun showEmptyProgress(show: Boolean) {
        view.showChecksProgress(show, true)
    }

    override fun showEmptyError(show: Boolean, error: Throwable?) {
        view.showChecksError(show)
    }

    override fun showEmptyView(show: Boolean) {
        view.showEmptyView(show)
    }

    override fun showData(show: Boolean, data: List<Receipt>) {
        view.showChecks(data)
    }

    override fun showErrorMessage(error: Throwable) {
        view.showMessage(error.message ?: "Error: No message :(")
    }

    override fun showRefreshProgress(show: Boolean) {
        view.showChecksProgress(show, true)
    }

    override fun showPageProgress(show: Boolean) {
        //view.showChecksProgress(show, false)
    }

    override fun loadingDone() {
        view.loadingDone()
    }

    override fun noMoreToLoad() {
        view.noMoreToLoad()
    }
}