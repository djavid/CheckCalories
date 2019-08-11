package com.djavid.checksonline.contracts.check

import com.djavid.checksonline.model.entities.Item
import com.djavid.checksonline.model.entities.Receipt

interface CheckContract {

    interface Presenter {
		fun init()
        fun showReceipt(receipt: Receipt)
		fun getCheck(checkId: String)
		fun onCheckReceived(receipt: Receipt)
		fun onDestroy()
    }

    interface View {
        fun init(presenter: Presenter)
		fun showGoods(goods: List<Item>)
		fun setToolbar(receipt: Receipt)
		fun showProgress(show: Boolean)
		fun setTitle(title: String)
		fun setAddress(address: String)
		fun setTotalSum(sum: Float)
		fun setLogo(shopTitle: String)
    }

    interface Navigator {
		fun openCheckPanel(receipt: Receipt)
    }

}