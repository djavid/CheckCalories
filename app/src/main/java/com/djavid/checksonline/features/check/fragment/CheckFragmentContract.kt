package com.djavid.checksonline.features.check.fragment

import com.djavid.checksonline.model.entities.Item
import com.djavid.checksonline.model.entities.Receipt

class CheckFragmentContract {

    interface View {
        fun init(presenter: Presenter)
        fun setGoods(checks: List<Item>)
        fun setToolbar(receipt: Receipt)
        fun showProgress(show: Boolean)
    }

    interface Presenter {
        fun init(checkId: String)
    }

}