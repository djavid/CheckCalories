package com.djavid.checksonline.toothpick.providers

import android.content.SharedPreferences
import com.djavid.checksonline.utils.SavedPreferences
import javax.inject.Inject
import javax.inject.Provider

class SavedPreferencesProvider @Inject constructor(
        private val sharedPreferences: SharedPreferences
) : Provider<SavedPreferences> {
    override fun get(): SavedPreferences {
        return SavedPreferences(sharedPreferences)
    }
}