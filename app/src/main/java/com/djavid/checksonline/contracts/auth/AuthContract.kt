package com.djavid.checksonline.contracts.auth

interface AuthContract {
	
	interface Presenter {
		fun init()
		fun onAuthClicked()
	}
	
	interface View {
		fun init(presenter: Presenter)
		fun finish()
	}
	
	interface Navigator {
		fun goToAuth()
	}
	
}