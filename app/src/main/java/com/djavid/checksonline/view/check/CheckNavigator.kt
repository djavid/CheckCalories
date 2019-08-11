package com.djavid.checksonline.view.check

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.djavid.checksonline.contracts.check.CheckContract
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.utils.EXTRA_RECEIPT

class CheckNavigator(
		private val fm: FragmentManager
) : CheckContract.Navigator {
	
	override fun openCheckPanel(receipt: Receipt) {
		val fragment = CheckFragment().apply {
			arguments = Bundle().apply {
				this.putSerializable(EXTRA_RECEIPT, receipt)
			}
		}
		fragment.show(fm, fragment.tag)
	}
	
}