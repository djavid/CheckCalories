package com.djavid.checksonline.view.common

import android.view.ViewGroup
import com.djavid.checksonline.utils.show
import kotlinx.android.synthetic.main.layout_error_action.view.*

class EmptyViewHolder(
        btn_title: String,
        description: String,
        private val view: ViewGroup,
        private val clickListener: () -> Unit = {}
) {

    init {
        view.btn_do_action.setOnClickListener { clickListener.invoke() }
        view.btn_do_action.text = btn_title
        view.tv_description.text = description
    }

    fun show(show: Boolean) {
        view.show(show)
    }

    fun showEmptyError(msg: String? = null) {
        view.show(true)
    }
}