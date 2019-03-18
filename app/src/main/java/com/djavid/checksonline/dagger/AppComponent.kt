package com.djavid.checksonline.dagger

import com.djavid.checksonline.features.app.App
import com.djavid.checksonline.features.app.AppModule
import com.djavid.checksonline.features.check.CheckFragment
import com.djavid.checksonline.features.habits.HabitsActivity
import com.djavid.checksonline.features.root.RootActivity
import com.djavid.checksonline.features.root.RootActivityModule
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ActivityBindings::class,
    FragmentBindings::class,
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    AppModule::class
])
interface AppComponent : AndroidInjector<App>

@Module
interface FragmentBindings {

    @ContributesAndroidInjector(modules = [])
    @UIScope
    fun checkFragment(): CheckFragment
}

@Module
interface ActivityBindings {

    @ContributesAndroidInjector(modules = [RootActivityModule::class])
    @UIScope
    fun rootActivity(): RootActivity

    @ContributesAndroidInjector(modules = [])
    @UIScope
    fun habitsActivity(): HabitsActivity

}

