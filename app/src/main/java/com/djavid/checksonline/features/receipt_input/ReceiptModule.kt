package com.djavid.checksonline.features.receipt_input

import android.view.View
import com.djavid.checksonline.features.root.ViewRoot
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_receipt_input.*

@Module(includes = [Bindings::class])
class ReceiptFragmentModule {

    @Provides
    @ViewRoot
    fun provideHabitsViewRoot(fragment: ReceiptInputFragment): View = fragment.receiptFragment

}

@Module
interface Bindings {

    @Binds
    fun bindReceiptPresenter(impl: ReceiptPresenter): ReceiptContract.Presenter

    @Binds
    fun bindReceiptView(view: ReceiptView): ReceiptContract.View

}