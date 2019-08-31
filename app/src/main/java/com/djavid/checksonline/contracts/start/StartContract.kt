package com.djavid.checksonline.contracts.start

interface StartContract {
	
	interface Presenter {
		fun init()
	}
	
	interface View {
		fun init(presenter: Presenter)
		fun finish()
	}
	
}