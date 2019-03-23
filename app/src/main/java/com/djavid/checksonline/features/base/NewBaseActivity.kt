package com.djavid.checksonline.features.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.djavid.checksonline.utils.show
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.layout_progress.*

@SuppressLint("Registered")
abstract class NewBaseActivity : AppCompatActivity() {

    protected abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        AndroidInjection.inject(this)
    }

    fun showProgress(show: Boolean) {
        progressLayout.show(show)
    }

    fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}