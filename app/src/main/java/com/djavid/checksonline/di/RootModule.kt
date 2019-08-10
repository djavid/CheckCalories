package com.djavid.checksonline.di

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.djavid.checksonline.contracts.root.RootContract
import com.djavid.checksonline.contracts.root.RootPresenter
import com.djavid.checksonline.utils.MODULE_ROOT
import com.djavid.checksonline.utils.TAG_BOTTOM_SHEET
import com.djavid.checksonline.view.check.BottomSheetNavigator
import com.djavid.checksonline.view.root.RootActivity
import com.djavid.checksonline.view.root.RootNavigator
import com.djavid.checksonline.view.root.RootView
import kotlinx.android.synthetic.main.activity_root.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class RootModule(activity: Activity) {
	val kodein = Kodein.Module(MODULE_ROOT) {
		bind<Activity>() with singleton { activity }
		
		bind<View>() with singleton { activity.rootActivity }
		
		bind<Lifecycle>() with singleton { (activity as RootActivity).lifecycle }
		
		bind<FragmentManager>() with singleton { (activity as RootActivity).supportFragmentManager }
		
		bind<ViewGroup>(TAG_BOTTOM_SHEET) with singleton { activity.root_bottomSheet }
		
		bind<RootContract.Presenter>() with singleton { RootPresenter(instance()) }
		
		bind<RootContract.View>() with singleton { RootView(instance(), instance()) }
		
		bind<RootContract.Navigator>() with singleton { RootNavigator() }
		
		bind<RootContract.BottomSheet>() with singleton {
			BottomSheetNavigator(instance(), instance(), instance())
		}
	}
}