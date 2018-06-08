package com.djavid.checksonline.presenter.habits

import com.djavid.checksonline.base.BaseView
import com.djavid.checksonline.model.networking.responses.GetHabitsResponse

interface HabitsView : BaseView {

    fun showHabits(habits: GetHabitsResponse.Result)

}