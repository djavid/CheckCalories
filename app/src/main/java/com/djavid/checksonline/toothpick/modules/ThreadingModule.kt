package com.djavid.checksonline.toothpick.modules

import com.djavid.checksonline.model.threading.AndroidSchedulersProvider
import com.djavid.checksonline.model.threading.SchedulersProvider
import toothpick.config.Module

class ThreadingModule : Module() {
    init {
        bind(SchedulersProvider::class.java).to(AndroidSchedulersProvider::class.java)
                .singletonInScope()
    }
}