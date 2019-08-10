package com.djavid.checksonline.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class NewBasePresenter {

    private val destroyDisposable = CompositeDisposable()

    abstract fun init()

    fun unsubscribeOnDestroy(d: Disposable) {
        destroyDisposable.add(d)
    }

    fun onDestroy() {
        destroyDisposable.clear()
    }

}