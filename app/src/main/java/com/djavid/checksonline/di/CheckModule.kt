package com.djavid.checksonline.di

import android.view.View
import androidx.fragment.app.Fragment
import com.djavid.checksonline.contracts.check.CheckContract
import com.djavid.checksonline.contracts.check.CheckPresenter
import com.djavid.checksonline.utils.MODULE_CHECK
import com.djavid.checksonline.utils.MODULE_CHECK_NAVIGATOR
import com.djavid.checksonline.view.check.CheckNavigator
import com.djavid.checksonline.view.check.CheckView
import com.djavid.checksonline.view.check.GoodsAdapter
import kotlinx.android.synthetic.main.fragment_check.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class CheckModule(fragment: Fragment) {
	val kodein = Kodein.Module(MODULE_CHECK) {
		bind<View>() with singleton { fragment.checkFragment }
		
		bind<GoodsAdapter>() with singleton { GoodsAdapter(fragment.requireContext()) }
		
		bind<CheckContract.Presenter>() with singleton { CheckPresenter(instance(), instance()) }
		
		bind<CheckContract.View>() with singleton { CheckView(instance(), instance(), instance()) }
	}
}

class CheckNavigatorModule {
	val kodein = Kodein.Module(MODULE_CHECK_NAVIGATOR) {
		bind<CheckContract.Navigator>() with singleton { CheckNavigator(instance()) }
	}
}