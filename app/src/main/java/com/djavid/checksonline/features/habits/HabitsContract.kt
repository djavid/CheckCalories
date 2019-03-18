package com.djavid.checksonline.features.habits

import com.djavid.checksonline.model.entities.Receipt
import com.djavid.checksonline.model.networking.responses.GetHabitsResponse

interface HabitsContract {

    interface View {
        fun init(presenter: HabitsContract.Presenter)
        fun showHabits(habits: GetHabitsResponse.Result)
        fun initRecycler()
        fun initChart()
        fun setChart()
    }

    interface Presenter {
        fun init()
        fun getHabits()
        fun onCheckClicked(receipt: Receipt)
    }

    interface Navigation {

    }
}