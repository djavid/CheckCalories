package com.djavid.checksonline.utils

import io.reactivex.disposables.Disposable

interface DisposableLifecycle {
    fun unsubscribeOnDetach(d: Disposable)
    fun unsubscribeOnDestroy(d: Disposable)
}