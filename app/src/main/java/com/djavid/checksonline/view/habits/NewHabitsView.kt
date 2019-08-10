package com.djavid.checksonline.view.habits

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.djavid.checksonline.R
import com.djavid.checksonline.contracts.habits.HabitsContract
import com.djavid.checksonline.model.entities.Habit
import com.djavid.checksonline.model.networking.responses.GetHabitsResponse
import com.djavid.checksonline.view.check.CheckItem
import com.djavid.checksonline.view.habits.items.HabitGoodItem
import com.djavid.checksonline.view.habits.items.HabitTitleItem
import com.djavid.checksonline.view.habits.items.PopularShopItem
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_habits.view.*
import kotlinx.android.synthetic.main.toolbar.view.*

class NewHabitsView constructor(
		private val viewRoot: View
): HabitsContract.View {

    private lateinit var presenter: HabitsContract.Presenter

    override fun init(presenter: HabitsContract.Presenter) {
        this.presenter = presenter
        initRecycler()

        viewRoot.btn_back.setOnClickListener {
            //presenter.onBackPressed() TODO router
        }
        viewRoot.toolbar_title.text = viewRoot.context.getString(R.string.title_habits)
    }

    override fun showHabits(habits: GetHabitsResponse.Result) {
        viewRoot.habits_placeholder.removeAllViews()

        val items = arrayOf(
                HabitTitleItem(viewRoot.context, Habit.POPULAR_SHOP),
                PopularShopItem(viewRoot.context, habits.popularShop),
                HabitTitleItem(viewRoot.context, Habit.POPULAR_ITEM),
                HabitGoodItem(viewRoot.context, habits.popularItem),
                HabitTitleItem(viewRoot.context, Habit.EXPENSIVE_ITEM),
                HabitGoodItem(viewRoot.context, habits.mostExpensiveItem),
                HabitTitleItem(viewRoot.context, Habit.EXPENSIVE_CHECK),
                CheckItem(viewRoot.context, habits.mostExpensiveCheck, presenter::onCheckClicked)
        )

        items.forEach {
            viewRoot.habits_placeholder.addView(it)
        }
    }

    override fun initRecycler() {
        viewRoot.habits_placeholder.isNestedScrollingEnabled = false
        viewRoot.habits_placeholder.builder
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(LinearLayoutManager(viewRoot.context))
    }

    override fun initChart() {
        viewRoot.line_chart.legend.isEnabled = false
        viewRoot.line_chart.description = null
        viewRoot.line_chart.animateY(1400, Easing.EasingOption.EaseInOutQuad)
    }

    override fun setChart() {
        val entries = mutableListOf<Entry>()

        entries.add(Entry(0f, 234f))
        entries.add(Entry(1f, 334f))
        entries.add(Entry(2f, 234f))
        entries.add(Entry(3f, 2434f))
        entries.add(Entry(4f, 234f))
        entries.add(Entry(5f, 23f))
        entries.add(Entry(6f, 2134f))
        entries.add(Entry(7f, 234f))
        entries.add(Entry(8f, 24f))
        entries.add(Entry(9f, 34f))
        entries.add(Entry(10f, 4f))
        entries.add(Entry(11f, 0f))

        val dataSet = LineDataSet(entries, "")

        val lineData = LineData(dataSet)

        viewRoot.line_chart.data = lineData
        viewRoot.line_chart.invalidate()
    }
}