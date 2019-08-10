package com.djavid.checksonline.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.djavid.checksonline.utils.show
import kotlinx.android.synthetic.main.layout_progress.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity(), KodeinAware {
	
	protected abstract val layoutResId: Int
	
	override lateinit var kodein: Kodein
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(layoutResId)
	}
	
	fun showProgress(show: Boolean) {
		progressLayout.show(show)
	}
	
	fun showToast(text: String) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
	}
}