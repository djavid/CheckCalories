package com.djavid.checksonline.toothpick.modules

import com.djavid.checksonline.model.entities.DateInterval
import toothpick.config.Module

class StatsItemModule(interval: DateInterval) : Module() {

    init {
        this.bind(DateInterval::class.java).toInstance(interval)
    }

}