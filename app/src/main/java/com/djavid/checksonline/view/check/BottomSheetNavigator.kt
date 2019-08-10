package com.djavid.checksonline.view.check

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.djavid.checksonline.R
import com.djavid.checksonline.contracts.root.RootContract
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.utils.EXTRA_RECEIPT
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetNavigator constructor(
		private val context: Context,
		private val bottomSheet: ViewGroup,
		private val fragmentManager: FragmentManager
) : RootContract.BottomSheet {
	
	override fun showSheet() {
//        BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_COLLAPSED
		BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_HALF_EXPANDED
		setSheetHeight(0.85)
	}
	
	private fun setSheetHeight(value: Double) {
		val displayMetrics = DisplayMetrics()
		(context as AppCompatActivity).windowManager.defaultDisplay.getMetrics(displayMetrics)
		
		bottomSheet.layoutParams = bottomSheet.layoutParams.apply {
			height = (displayMetrics.heightPixels * value).toInt()
		}
		
		BottomSheetBehavior.from(bottomSheet).peekHeight = (displayMetrics.heightPixels * value).toInt()
	}
	
	override fun hideSheet() {
		BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_HIDDEN
	}
	
	override fun openCheck(receipt: Receipt) {
		fragmentManager.beginTransaction().add(R.id.container, CheckFragment().apply {
			this.arguments = Bundle().apply {
				this.putSerializable(EXTRA_RECEIPT, receipt)
			}
		}).commit()
		showSheet()
	}
	
}