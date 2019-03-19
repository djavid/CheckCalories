package com.djavid.checksonline.dagger

import com.djavid.checksonline.features.app.App
import com.djavid.checksonline.features.app.AppModule
import com.djavid.checksonline.features.check.CheckActivity
import com.djavid.checksonline.features.check.CheckFragment
import com.djavid.checksonline.features.checks.ChecksFragment
import com.djavid.checksonline.features.habits.HabitsActivity
import com.djavid.checksonline.features.habits.HabitsActivityModule
import com.djavid.checksonline.features.habits.HabitsFragment
import com.djavid.checksonline.features.habits.HabitsFragmentModule
import com.djavid.checksonline.features.qrcode.QRCodeActivity
import com.djavid.checksonline.features.qrcode.QRCodeActivityModule
import com.djavid.checksonline.features.receipt_input.ReceiptFragmentModule
import com.djavid.checksonline.features.receipt_input.ReceiptInputActivity
import com.djavid.checksonline.features.receipt_input.ReceiptInputFragment
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

    @ContributesAndroidInjector(modules = [])
    @UIScope
    fun checksFragment(): ChecksFragment

    @ContributesAndroidInjector(modules = [ReceiptFragmentModule::class])
    @UIScope
    fun receiptInputFragment(): ReceiptInputFragment

    @ContributesAndroidInjector(modules = [HabitsFragmentModule::class])
    @UIScope
    fun habitsFragment(): HabitsFragment
}

@Module
interface ActivityBindings {

    @ContributesAndroidInjector(modules = [RootActivityModule::class])
    @UIScope
    fun rootActivity(): RootActivity

    @ContributesAndroidInjector(modules = [])
    @UIScope
    fun checkActivity(): CheckActivity

    @ContributesAndroidInjector(modules = [HabitsActivityModule::class])
    @UIScope
    fun habitsActivity(): HabitsActivity

    @ContributesAndroidInjector(modules = [])
    @UIScope
    fun receiptInputActivity(): ReceiptInputActivity

    @ContributesAndroidInjector(modules = [QRCodeActivityModule::class])
    @UIScope
    fun qrcodeActivity(): QRCodeActivity

}

