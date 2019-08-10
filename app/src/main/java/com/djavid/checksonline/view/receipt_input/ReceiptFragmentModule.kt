package com.djavid.checksonline.view.receipt_input

import android.view.View
import kotlinx.android.synthetic.main.fragment_receipt_input.*

@Module(includes = [Bindings::class])
class ReceiptFragmentModule {

    @Provides
    fun provideHabitsViewRoot(fragment: ReceiptInputFragment): View = fragment.receiptFragment

}

@Module
interface Bindings {

    @Binds
    fun bindReceiptPresenter(impl: ReceiptPresenter): ReceiptContract.Presenter

    @Binds
    fun bindReceiptView(view: ReceiptView): ReceiptContract.View

}