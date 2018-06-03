package com.djavid.checksonline.utils

import android.content.SharedPreferences
import javax.inject.Inject

class SavedPreferences @Inject constructor(
        private val sharedPreferences: SharedPreferences
) {

    fun getToken() = sharedPreferences.getString(Preferences.TOKEN, "")

    fun setToken(token: String) = sharedPreferences.edit()
            .putString(Preferences.TOKEN, token)
            .apply()

    fun getIsShop() = sharedPreferences.getBoolean(Preferences.IS_SHOP, false)

    fun setIsShop(isShop: Boolean) = sharedPreferences.edit()
            .putBoolean(Preferences.IS_SHOP, isShop)
            .apply()

    fun getTotalSumInterval() =
            sharedPreferences.getString(Preferences.TOTAL_SUM_INTERVAL, "TOTAL")

    fun setTotalSumInterval(interval: String) = sharedPreferences.edit()
            .putString(Preferences.TOTAL_SUM_INTERVAL, interval)
            .apply()

}