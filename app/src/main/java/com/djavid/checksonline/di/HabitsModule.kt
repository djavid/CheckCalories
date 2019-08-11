package com.djavid.checksonline.di

import android.view.View
import androidx.fragment.app.Fragment
import com.djavid.checksonline.contracts.habits.HabitsContract
import com.djavid.checksonline.contracts.habits.HabitsPresenter
import com.djavid.checksonline.utils.MODULE_HABITS
import com.djavid.checksonline.view.habits.HabitsView
import kotlinx.android.synthetic.main.fragment_habits.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class HabitsModule(fragment: Fragment) {
	val kodein = Kodein.Module(MODULE_HABITS) {
		bind<View>() with singleton { fragment.habitsFragment }
		bind<HabitsContract.Presenter>() with singleton { HabitsPresenter(instance(), instance()) }
		bind<HabitsContract.View>() with singleton { HabitsView(instance()) }
	}
}