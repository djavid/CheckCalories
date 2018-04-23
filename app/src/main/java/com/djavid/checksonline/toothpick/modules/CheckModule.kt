package com.djavid.checksonline.toothpick.modules

import com.djavid.checksonline.toothpick.qualifiers.CheckId
import toothpick.config.Module

class CheckModule(checkId: String) : Module() {

    init {
        this.bind(String::class.java).withName(CheckId::class.java).toInstance(checkId)
    }
}