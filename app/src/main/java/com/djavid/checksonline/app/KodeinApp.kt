package com.djavid.checksonline.app

import android.app.Activity
import androidx.fragment.app.Fragment
import org.kodein.di.Kodein

interface KodeinApp {
	fun authModule(activity: Activity): Kodein
	fun startModule(activity: Activity): Kodein
	fun rootModule(activity: Activity): Kodein
	fun checksModule(fragment: Fragment): Kodein
	fun checkModule(fragment: Fragment): Kodein
	fun qrModule(fragment: Fragment): Kodein
	fun checkInputModule(fragment: Fragment): Kodein
	fun habitsModule(fragment: Fragment): Kodein
}