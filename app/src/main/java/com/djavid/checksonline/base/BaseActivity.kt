package com.djavid.checksonline.base

import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.djavid.checksonline.utils.visible
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.layout_progress.*

abstract class BaseActivity : MvpAppCompatActivity(), BaseView {

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(message: String) {
        Toasty.error(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress(show: Boolean) {
        progressLayout.visible(show)
    }
}