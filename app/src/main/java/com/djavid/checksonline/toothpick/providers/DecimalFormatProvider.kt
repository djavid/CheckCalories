package com.djavid.checksonline.toothpick.providers

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import javax.inject.Inject
import javax.inject.Provider

class DecimalFormatProvider @Inject constructor() : Provider<DecimalFormat> {

    override fun get(): DecimalFormat {
        val symbols = DecimalFormatSymbols.getInstance()
        symbols.groupingSeparator = ' '

        return DecimalFormat("###,###", symbols)
    }
}