package com.djavid.checksonline.features.checks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.djavid.checksonline.R
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ChecksFragment : Fragment() {

    @Inject
    lateinit var presenter: ChecksContract.Presenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_checks, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        presenter.init()
    }

}
