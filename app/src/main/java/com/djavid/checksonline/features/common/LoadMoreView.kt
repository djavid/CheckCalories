package com.djavid.checksonline.features.common

import com.djavid.checksonline.R
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.infinite.LoadMore

@Layout(R.layout.load_more_view)
class LoadMoreView(
        private val callback: Callback
) {

    @LoadMore
    fun onLoadMore() {
        callback.onShowMore()
    }

    interface Callback {
        fun onShowMore()
    }

}