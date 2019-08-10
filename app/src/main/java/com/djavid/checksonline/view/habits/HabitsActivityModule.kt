package com.djavid.checksonline.view.habits

import android.view.View
import kotlinx.android.synthetic.main.activity_habits.*

@Module
class HabitsActivityModule {

    @Provides
    fun provideViewRoot(activity: HabitsActivity): View = activity.habitsActivity

}