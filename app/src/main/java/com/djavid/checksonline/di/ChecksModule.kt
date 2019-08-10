package com.djavid.checksonline.di

import android.view.View
import androidx.fragment.app.Fragment
import com.djavid.checksonline.contracts.checks.ChecksContract
import com.djavid.checksonline.contracts.checks.ChecksPresenter
import com.djavid.checksonline.utils.MODULE_CHECKS
import com.djavid.checksonline.view.checks.ChecksView
import kotlinx.android.synthetic.main.fragment_checks.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ChecksModule(fragment: Fragment) {
	val kodein = Kodein.Module(MODULE_CHECKS) {
		bind<View>() with singleton { fragment.checksFragment }
		
		//bind<RootContract.BottomSheet>() with singleton { (fragment.activity as RootActivity).bottomSheet }
		
		bind<ChecksContract.Presenter>() with singleton {
			ChecksPresenter(instance(), instance(), instance(), instance(), instance())
		}
		
		bind<ChecksContract.View>() with singleton { ChecksView(instance()) }
	}
}