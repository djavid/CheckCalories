package com.djavid.checksonline.utils

import toothpick.configuration.Configuration

class Injection : AbstractInjection() {
    override fun getToothpickConfig(): Configuration =
            Configuration.forDevelopment().preventMultipleRootScopes()
}