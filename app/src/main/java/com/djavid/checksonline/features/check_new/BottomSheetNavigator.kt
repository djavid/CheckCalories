package com.djavid.checksonline.features.check_new

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.djavid.checksonline.R
import com.djavid.checksonline.features.app.EXTRA_RECEIPT
import com.djavid.checksonline.features.root.BottomSheet
import com.djavid.checksonline.features.root.OriginActivityContext
import com.djavid.checksonline.features.root.RootContract
import com.djavid.checksonline.model.entities.Receipt
import com.google.android.material.bottomsheet.BottomSheetBehavior
import javax.inject.Inject

class BottomSheetNavigator @Inject constructor(
        @OriginActivityContext private val context: Context,
        @BottomSheet private val bottomSheet: ViewGroup,
        private val fragmentManager: FragmentManager
) : RootContract.BottomSheet {

    override fun showSheet() {
        setSheetHeight(0.7)
        BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
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
        fragmentManager.beginTransaction().add(R.id.container, NewCheckFragment().apply {
            this.arguments = Bundle().apply {
                this.putSerializable(EXTRA_RECEIPT, receipt)
            }
        }).commit()
        showSheet()
    }

}