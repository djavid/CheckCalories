package com.djavid.checksonline.presenter.check

import com.djavid.checksonline.base.BaseView
import com.djavid.checksonline.model.entities.Item

interface CheckView : BaseView {
    fun setGoods(checks: List<Item>)
    fun setToolbarSum(sum: Long)
    fun setToolbarAddress(address: String?)
    fun setDatetime(date: String?)
}