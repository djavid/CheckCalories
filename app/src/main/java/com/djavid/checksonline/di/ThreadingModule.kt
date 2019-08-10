package com.djavid.checksonline.di

import com.djavid.checksonline.model.threading.AndroidSchedulersProvider
import com.djavid.checksonline.model.threading.SchedulersProvider
import com.djavid.checksonline.utils.MODULE_THREADING
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

class ThreadingModule {
	val kodein = Kodein.Module(MODULE_THREADING) {
		bind<SchedulersProvider>() with singleton { AndroidSchedulersProvider() }
	}
}