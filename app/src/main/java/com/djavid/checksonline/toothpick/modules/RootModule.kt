package com.djavid.checksonline.toothpick.modules

import com.djavid.checksonline.toothpick.providers.DecimalFormatProvider
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module
import java.text.DecimalFormat

class RootModule : Module() {

    init {

        bind(DecimalFormat::class.java)
                .toProvider(DecimalFormatProvider::class.java)
                .providesSingletonInScope()

    }
}