package com.djavid.checksonline.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.djavid.checksonline.utils.visible
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.layout_progress.*

abstract class BaseFragment : MvpAppCompatFragment(), BaseView {

    protected abstract val layoutResId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutResId, container, false)


    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(message: String) {
        context ?: return

        Toasty.error(context!!, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress(show: Boolean) {
        progressLayout.visible(show)
    }

}