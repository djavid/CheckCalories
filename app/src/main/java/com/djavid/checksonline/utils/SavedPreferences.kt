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

}