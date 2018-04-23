package com.djavid.checksonline.base

import android.view.ViewGroup
import com.djavid.checksonline.utils.visible
import kotlinx.android.synthetic.main.layout_need_permission.view.*

class EmptyViewHolder(
        private val view: ViewGroup,
        private val clickListener: () -> Unit = {}
) {

    init {
        view.btn_allow_permission.setOnClickListener { clickListener.invoke() }
    }

    fun showEmptyData() {
        view.visible(true)
    }

    fun hide() {
        view.visible(false)
    }

    fun showEmptyError(msg: String? = null) {
        view.visible(true)
    }
}