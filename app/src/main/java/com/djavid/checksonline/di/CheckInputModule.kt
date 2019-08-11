package com.djavid.checksonline.di

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.djavid.checksonline.contracts.check_input.CheckInputContract
import com.djavid.checksonline.contracts.check_input.CheckInputPresenter
import com.djavid.checksonline.utils.MODULE_RECEIPT_INPUT
import com.djavid.checksonline.view.receipt_input.CheckInputView
import kotlinx.android.synthetic.main.fragment_receipt_input.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class CheckInputModule(fragment: Fragment) {
	val kodein = Kodein.Module(MODULE_RECEIPT_INPUT) {
		bind<View>() with singleton { fragment.receiptFragment }
		bind<CheckInputContract.Presenter>() with singleton { CheckInputPresenter(instance(), instance(), instance()) }
		bind<CheckInputContract.View>() with singleton { CheckInputView(instance()) }
		bind<Lifecycle>() with singleton { fragment.lifecycle }
	}
}