package com.djavid.checksonline.contracts.check

import com.djavid.checksonline.interactors.ChecksInteractor
import com.djavid.checksonline.model.entities.Receipt
import io.reactivex.disposables.Disposable

class CheckPresenter constructor(
		private val view: CheckContract.View,
        private val interactor: ChecksInteractor
) : CheckContract.Presenter {

    private var disposable: Disposable? = null
    
    override fun init() {
        view.init(this)
        //getCheck(checkId)
    }

    override fun getCheck(checkId: String) {
        disposable = interactor.getCheck(checkId.toLong())
                .doOnSubscribe { view.showProgress(true) }
                .doAfterTerminate { view.showProgress(false) }
                .subscribe({
                    onCheckReceived(it)
                }, {
                    //TODO processError(it)
                })
    }
	
	override fun showReceipt(receipt: Receipt) {
        receipt.user?.let {
            view.setTitle(it)
            view.setLogo(it)
        }
        
        receipt.retailPlaceAddress?.let {
            view.setAddress(it)
        }
        
        view.setTotalSum(receipt.totalSum / 100f)
        
        view.showGoods(receipt.items)
	}

    override fun onCheckReceived(receipt: Receipt) {
        view.showGoods(receipt.items)
        view.setToolbar(receipt)
    }

    override fun onDestroy() {
        disposable?.dispose()
    }

}