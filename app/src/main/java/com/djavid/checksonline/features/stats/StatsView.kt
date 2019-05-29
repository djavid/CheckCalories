package com.djavid.checksonline.features.stats

import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.djavid.checksonline.R
import com.djavid.checksonline.features.root.ViewRoot
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.model.entities.Dates
import com.djavid.checksonline.utils.show
import kotlinx.android.synthetic.main.fragment_stats.view.*
import kotlinx.android.synthetic.main.layout_progress.view.*
import kotlinx.android.synthetic.main.toolbar_stats.view.*
import java.util.*
import javax.inject.Inject

class StatsView @Inject constructor(
        @ViewRoot private val view: View,
        private val fragment: Fragment
) : StatsContract.View {

    private lateinit var presenter: StatsContract.Presenter

    override fun init(presenter: StatsContract.Presenter) {
        this.presenter = presenter

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_stats)
        (fragment.activity as? AppCompatActivity)?.setSupportActionBar(toolbar)

        setPopupMenu()
        view.btn_habits.setOnClickListener { presenter.onHabitsClicked() }
    }

    private fun setPopupMenu() {
        val popupMenu = PopupMenu(view.context, view.btn_period)
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

        view.btn_period.setOnClickListener {
            popupMenu.show()
        }
    }

    override fun setToolbarSum(totalSum: Double) {
        view.price.post {
            view.price.text = view.context?.getString(R.string.format_float)?.format(Locale.ROOT, totalSum)
        }
    }

    override fun setViewPager(intervals: List<DateInterval>) {
        fragment.childFragmentManager.let {
            view.viewpager.adapter = StatsPagerAdapter(
                    view.context ?: throw NullPointerException("Context is null"),
                    fragment.childFragmentManager,
                    intervals
            )
        }

        view.viewpager.apply {
            currentItem = adapter?.count ?: 0
        }

        view.viewpager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                presenter.onViewPagerScrolled(position)
            }
        })
    }

    override fun showProgress(show: Boolean) {
        view.progressLayout.show(show)
    }
}