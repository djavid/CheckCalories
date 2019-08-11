package com.djavid.checksonline.view.check

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.djavid.checksonline.R
import com.djavid.checksonline.app.KodeinApp
import com.djavid.checksonline.contracts.check.CheckContract
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.utils.EXTRA_RECEIPT
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class CheckFragment : BottomSheetDialogFragment(), KodeinAware {
	
	override lateinit var kodein: Kodein
	
	private val presenter: CheckContract.Presenter by instance()
	
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_check, container, false)
	}
	
	override fun getTheme() = R.style.BottomSheetDialog
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		kodein = (requireContext().applicationContext as KodeinApp).checkModule(this)
		presenter.init()
		
		setWhiteNavigationBar()
		
		arguments?.getSerializable(EXTRA_RECEIPT)?.let {
			presenter.showReceipt(it as Receipt)
		}
	}
	
	private fun setWhiteNavigationBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
			dialog?.window?.let {
				val metrics = DisplayMetrics()
				it.windowManager.defaultDisplay.getMetrics(metrics)
				
				val dimDrawable = GradientDrawable()
				val navigationBarDrawable = GradientDrawable().apply {
					shape = GradientDrawable.RECTANGLE
					setColor(Color.WHITE)
				}
				val layers = arrayOf<Drawable>(dimDrawable, navigationBarDrawable)
				
				val windowBackground = LayerDrawable(layers).apply {
					setLayerInsetTop(1, metrics.heightPixels)
				}
				
				it.setBackgroundDrawable(windowBackground)
			}
		}
	}
	
}