package com.djavid.checksonline.features.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.djavid.checksonline.utils.show
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.layout_progress.*

abstract class NewBaseFragment: Fragment() {

    protected abstract val layoutResId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutResId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
    }

    fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    open fun showProgress(show: Boolean) {
        progressLayout.show(show)
    }
}