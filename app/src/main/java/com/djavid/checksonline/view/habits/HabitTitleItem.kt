package com.djavid.checksonline.view.habits

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.djavid.checksonline.R
import com.djavid.checksonline.model.entities.Habit
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Position
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View

@Layout(R.layout.layout_card_title)
class HabitTitleItem(
        private val context: Context?,
        private val habit: Habit
) {

    @View(R.id.iv_habit_icon)
    lateinit var iv_habit_icon: ImageView

    @View(R.id.tv_habit_title)
    lateinit var tv_habit_title: TextView

    @JvmField
    @Position
    var position: Int = 0

    @Resolve
    fun onResolved() {

        when (habit) {
            Habit.POPULAR_SHOP -> {
                tv_habit_title.text = context?.getString(R.string.popular_shop)
                iv_habit_icon.setImageResource(R.drawable.ic_heart)
            }
            Habit.POPULAR_ITEM -> {
                tv_habit_title.text = context?.getString(R.string.popular_item)
                iv_habit_icon.setImageResource(R.drawable.ic_groceries)
            }
            Habit.EXPENSIVE_ITEM -> {
                tv_habit_title.text = context?.getString(R.string.most_expensive_item)
                iv_habit_icon.setImageResource(R.drawable.ic_coins)
            }
            Habit.EXPENSIVE_CHECK -> {
                tv_habit_title.text = context?.getString(R.string.most_expensive_receipt)
                iv_habit_icon.setImageResource(R.drawable.ic_bag)
            }
        }
    }

}