package com.djavid.checksonline.presenter.check

import com.djavid.checksonline.base.BaseView
import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.model.networking.responses.FlaskResponse

interface CheckView : BaseView {
    fun setGoods(checks: List<Receipt.Item>)
    fun setToolbarSum(sum: Long)
    fun setToolbarAddress(address: String?)
    fun setDatetime(date: String?)
    fun setCategories(categories: FlaskResponse)
}