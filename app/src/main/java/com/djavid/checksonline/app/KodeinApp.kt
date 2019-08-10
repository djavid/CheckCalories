package com.djavid.checksonline.app

import android.app.Activity
import androidx.fragment.app.Fragment
import org.kodein.di.Kodein

interface KodeinApp {
	fun rootModule(activity: Activity): Kodein
	fun checksModule(fragment: Fragment): Kodein
	fun checkModule(fragment: Fragment): Kodein
}