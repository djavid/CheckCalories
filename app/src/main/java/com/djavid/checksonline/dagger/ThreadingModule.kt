package com.djavid.checksonline.dagger

import com.djavid.checksonline.model.threading.AndroidSchedulersProvider
import com.djavid.checksonline.model.threading.SchedulersProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ThreadingModule {

    @Binds
    @Singleton
    fun bindsSchedulersProvider(provide: AndroidSchedulersProvider): SchedulersProvider
}