package com.djavid.checksonline.di

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.djavid.checksonline.contracts.check.CheckContract
import com.djavid.checksonline.contracts.check.CheckPresenter
import com.djavid.checksonline.utils.MODULE_CHECK
import com.djavid.checksonline.view.check.CheckView
import kotlinx.android.synthetic.main.fragment_check.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class CheckModule(fragment: Fragment) {
	val kodein = Kodein.Module(MODULE_CHECK) {
		bind<View>() with singleton { fragment.checkFragment }
		
		bind<CheckContract.Presenter>() with singleton { CheckPresenter(instance(), instance()) }
		
		bind<CheckContract.View>() with singleton { CheckView(instance()) }
		
		bind<FragmentManager>() with singleton { fragment.childFragmentManager }
	}
}