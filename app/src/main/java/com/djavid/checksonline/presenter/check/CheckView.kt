package com.djavid.checksonline.presenter.check

import com.djavid.checksonline.base.BaseView
import com.djavid.checksonline.model.entities.Item
import com.djavid.checksonline.model.entities.Receipt

interface CheckView : BaseView {
    fun setGoods(checks: List<Item>)
    fun setToolbar(receipt: Receipt)
}