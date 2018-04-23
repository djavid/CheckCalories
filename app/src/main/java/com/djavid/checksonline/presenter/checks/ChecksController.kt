package com.djavid.checksonline.presenter.checks

import com.djavid.checksonline.base.Paginator
import com.djavid.checksonline.model.entities.Receipt

class ChecksController(private val view: ChecksView) : Paginator.ViewController<Receipt> {

    override fun showEmptyProgress(show: Boolean) {
        view.showChecksProgress(show, true)
    }

    override fun showEmptyError(show: Boolean, error: Throwable?) {
        //view.showChecksError(show, error?.message ?: "Error: No message :(")
    }

    override fun showEmptyView(show: Boolean) {

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
        view.showChecksProgress(show, false)
    }

    override fun loadingDone() {
        view.loadingDone()
    }
}