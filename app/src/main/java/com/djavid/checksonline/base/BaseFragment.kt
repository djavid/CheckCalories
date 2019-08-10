package com.djavid.checksonline.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.djavid.checksonline.utils.show
import kotlinx.android.synthetic.main.layout_progress.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

abstract class BaseFragment : Fragment(), KodeinAware {

    protected abstract val layoutResId: Int
	
	override lateinit var kodein: Kodein

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutResId, container, false)

    fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    open fun showProgress(show: Boolean) {
        progressLayout.show(show)
    }

}