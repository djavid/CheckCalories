package com.djavid.checksonline.features.habits

import android.view.View
import com.djavid.checksonline.features.root.ViewRoot
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.activity_habits.*

@Module
class HabitsActivityModule {

    @Provides
    @ViewRoot
    fun provideViewRoot(activity: HabitsActivity): View = activity.habitsActivity

}