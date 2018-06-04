package com.djavid.checksonline.toothpick.modules

import com.djavid.checksonline.toothpick.qualifiers.Title
import toothpick.config.Module

class PercentageModule(title: String) : Module() {

    init {
        this.bind(String::class.java).withName(Title::class.java).toInstance(title)
    }
}