package com.djavid.checksonline.base

import com.arellomobile.mvp.MvpPresenter
import com.djavid.checksonline.utils.DisposableLifecycle
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.net.SocketTimeoutException

abstract class BasePresenter<V : BaseView>(
        protected val router: Router
) : MvpPresenter<V>(), DisposableLifecycle {

    private val detachDisposable = CompositeDisposable()
    private val destroyDisposable = CompositeDisposable()

    fun onBackPressed() {
        router.exit()
    }

    override fun unsubscribeOnDetach(d: Disposable) {
        detachDisposable.add(d)
    }

    override fun unsubscribeOnDestroy(d: Disposable) {
        destroyDisposable.add(d)
    }

    override fun detachView(view: V) {
        super.detachView(view)
        detachDisposable.clear()
    }

    override fun onDestroy() {
        destroyDisposable.clear()
    }

    protected fun processError(throwable: Throwable) {

        //viewState.showMessage(throwable.localizedMessage)

        if (throwable is SocketTimeoutException) {
            Timber.e(throwable)
            viewState.showErrorMessage("Истекло время ожидания сервера")
        }
    }

}