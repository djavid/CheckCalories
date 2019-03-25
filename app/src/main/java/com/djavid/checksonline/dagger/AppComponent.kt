package com.djavid.checksonline.dagger

import com.djavid.checksonline.features.app.App
import com.djavid.checksonline.features.categories.CategoriesFragment
import com.djavid.checksonline.features.categories.CategoriesModule
import com.djavid.checksonline.features.check.activity.CheckActivity
import com.djavid.checksonline.features.check.activity.CheckActivityModule
import com.djavid.checksonline.features.check.fragment.CheckFragment
import com.djavid.checksonline.features.check.fragment.CheckFragmentModule
import com.djavid.checksonline.features.checks.ChecksFragment
import com.djavid.checksonline.features.checks.ChecksModule
import com.djavid.checksonline.features.habits.HabitsActivity
import com.djavid.checksonline.features.habits.HabitsActivityModule
import com.djavid.checksonline.features.habits.HabitsFragment
import com.djavid.checksonline.features.habits.HabitsFragmentModule
import com.djavid.checksonline.features.qr.QRCodeActivityModule
import com.djavid.checksonline.features.qr.QrActivity
import com.djavid.checksonline.features.receipt_input.ReceiptFragmentModule
import com.djavid.checksonline.features.receipt_input.ReceiptInputActivity
import com.djavid.checksonline.features.receipt_input.ReceiptInputFragment
import com.djavid.checksonline.features.root.RootActivity
import com.djavid.checksonline.features.root.RootActivityModule
import com.djavid.checksonline.features.shops.ShopsFragment
import com.djavid.checksonline.features.shops.ShopsModule
import com.djavid.checksonline.features.stats.StatsFragment
import com.djavid.checksonline.features.stats.StatsListActivity
import com.djavid.checksonline.features.stats.StatsModule
import com.djavid.checksonline.features.stats_item.StatItemFragment
import com.djavid.checksonline.features.stats_item.StatItemModule
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

    @ContributesAndroidInjector(modules = [CheckFragmentModule::class])
    @UIScope
    fun checkFragment(): CheckFragment

    @ContributesAndroidInjector(modules = [ChecksModule::class])
    @UIScope
    fun checksFragment(): ChecksFragment

    @ContributesAndroidInjector(modules = [ReceiptFragmentModule::class])
    @UIScope
    fun receiptInputFragment(): ReceiptInputFragment

    @ContributesAndroidInjector(modules = [HabitsFragmentModule::class])
    @UIScope
    fun habitsFragment(): HabitsFragment

    @ContributesAndroidInjector(modules = [StatsModule::class])
    @UIScope
    fun statsFragment(): StatsFragment

    @ContributesAndroidInjector(modules = [CategoriesModule::class])
    @UIScope
    fun categoriesFragment(): CategoriesFragment

    @ContributesAndroidInjector(modules = [StatItemModule::class])
    @UIScope
    fun statItemFragment(): StatItemFragment

    @ContributesAndroidInjector(modules = [ShopsModule::class])
    @UIScope
    fun shopsFragment(): ShopsFragment
}

@Module
interface ActivityBindings {

    @ContributesAndroidInjector(modules = [RootActivityModule::class])
    @UIScope
    fun rootActivity(): RootActivity

    @ContributesAndroidInjector(modules = [CheckActivityModule::class])
    @UIScope
    fun checkActivity(): CheckActivity

    @ContributesAndroidInjector(modules = [HabitsActivityModule::class])
    @UIScope
    fun habitsActivity(): HabitsActivity

    @ContributesAndroidInjector(modules = [])
    @UIScope
    fun receiptInputActivity(): ReceiptInputActivity

    @ContributesAndroidInjector(modules = [])
    @UIScope
    fun statsActivity(): StatsListActivity

    @ContributesAndroidInjector(modules = [QRCodeActivityModule::class])
    @UIScope
    fun qrCodeActivity(): QrActivity

}

