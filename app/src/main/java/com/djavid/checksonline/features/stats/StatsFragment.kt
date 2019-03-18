package com.djavid.checksonline.features.stats

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.PopupMenu
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.djavid.checksonline.R
import com.djavid.checksonline.features.base.BaseFragment
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.model.entities.Dates
import kotlinx.android.synthetic.main.fragment_stats.*
import kotlinx.android.synthetic.main.toolbar_stats.*
import java.util.*

class StatsFragment : BaseFragment(), StatsView {

    companion object {
        fun newInstance(): StatsFragment = StatsFragment()
    }

    @InjectPresenter
    lateinit var presenter: StatsPresenter

    @ProvidePresenter
    fun providePresenter(): StatsPresenter =
            Toothpick.openScopes(activity, this)
                    .getInstance(StatsPresenter::class.java)

    override val layoutResId = R.layout.fragment_stats


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, Toothpick.openScopes(activity, this))

    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) Toothpick.closeScope(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_stats)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        setPopupMenu()
        btn_habits.setOnClickListener { presenter.onHabitsClicked() }
    }


    override fun setViewPager(intervals: List<DateInterval>) {

        viewpager.adapter = StatsPagerAdapter(
                context ?: throw NullPointerException("Context is null"),
                childFragmentManager,
                intervals
        )

        viewpager.apply {
            currentItem = adapter?.count ?: 0
        }

        viewpager.addOnPageChangeListener(object: ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                presenter.onViewPagerScrolled(position)
            }
        })
    }

    override fun setToolbarSum(totalSum: Double) {
        price.post {
            price.text = context?.getString(R.string.format_float)?.format(Locale.ROOT, totalSum)
        }
    }

    private fun setPopupMenu() {
        val popupMenu = PopupMenu(context, btn_period)
        popupMenu.inflate(R.menu.menu_stat_date_popup)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_month -> {
                    presenter.onDateIntervalChosen(Dates.MONTH)
                    true
                }
                R.id.menu_week -> {
                    presenter.onDateIntervalChosen(Dates.WEEK)
                    true
                }
                R.id.menu_day -> {
                    presenter.onDateIntervalChosen(Dates.DAY)
                    true
                }
                R.id.menu_own -> {
                    presenter.onDateIntervalChosen(Dates.OWN)
                    true
                }
                else -> false
            }
        }

        btn_period.setOnClickListener {
            popupMenu.show()
        }
    }

}
