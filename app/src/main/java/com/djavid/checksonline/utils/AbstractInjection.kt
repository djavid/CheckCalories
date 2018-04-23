package com.djavid.checksonline.utils

import toothpick.Toothpick
import toothpick.configuration.Configuration

abstract class AbstractInjection {

    fun initialize() {
        Toothpick.setConfiguration(this.getToothpickConfig())
        this.initRegistries()
    }

    protected abstract fun getToothpickConfig(): Configuration

    protected open fun initRegistries() {

    }

}