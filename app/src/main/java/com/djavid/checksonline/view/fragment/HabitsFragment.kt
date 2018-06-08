package com.djavid.checksonline.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseFragment
import com.djavid.checksonline.model.networking.responses.GetHabitsResponse
import com.djavid.checksonline.presenter.habits.HabitsPresenter
import com.djavid.checksonline.presenter.habits.HabitsView
import com.djavid.checksonline.view.Habit
import com.djavid.checksonline.view.adapters.CheckItem
import com.djavid.checksonline.view.adapters.HabitGoodItem
import com.djavid.checksonline.view.adapters.HabitTitleItem
import com.djavid.checksonline.view.adapters.PopularShopItem
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_habits.*
import kotlinx.android.synthetic.main.toolbar.*
import toothpick.Toothpick

class HabitsFragment : BaseFragment(), HabitsView {

    companion object {
        fun newInstance(): HabitsFragment = HabitsFragment()
    }

    @InjectPresenter
    lateinit var presenter: HabitsPresenter

    @ProvidePresenter
    fun providePresenter(): HabitsPresenter =
            Toothpick.openScopes(activity, this).getInstance(HabitsPresenter::class.java)

    override val layoutResId = R.layout.fragment_habits


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, Toothpick.openScopes(activity, this))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) Toothpick.closeScope(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_back.setOnClickListener { presenter.onBackPressed() }
        toolbar_title.text = context?.getString(R.string.title_habits)

        initPlaceholder()
        initChart()
        setChart()
    }

    override fun showHabits(habits: GetHabitsResponse.Result) {

        habits_placeholder.removeAllViews()

        habits_placeholder.addView(HabitTitleItem(context, Habit.POPULAR_SHOP))
        habits_placeholder.addView(PopularShopItem(context, habits.popularShop))

        habits_placeholder.addView(HabitTitleItem(context, Habit.POPULAR_ITEM))
        habits_placeholder.addView(HabitGoodItem(context, habits.popularItem))

        habits_placeholder.addView(HabitTitleItem(context, Habit.EXPENSIVE_ITEM))
        habits_placeholder.addView(HabitGoodItem(context, habits.mostExpensiveItem))

        habits_placeholder.addView(HabitTitleItem(context, Habit.EXPENSIVE_CHECK))
        habits_placeholder.addView(CheckItem(context, habits.mostExpensiveCheck, presenter::onCheckClicked))
    }

    private fun initPlaceholder() {
        habits_placeholder.isNestedScrollingEnabled = false
        habits_placeholder.builder
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(LinearLayoutManager(context))
    }

    private fun initChart() {

        line_chart.legend.isEnabled = false
        line_chart.description = null

        line_chart.animateY(1400, Easing.EasingOption.EaseInOutQuad)
    }

    fun setChart() {
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

        line_chart.data = lineData
        line_chart.invalidate()
    }

}
