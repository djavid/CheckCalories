package com.djavid.checksonline.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.djavid.checksonline.R
import com.djavid.checksonline.base.BaseFragment
import com.djavid.checksonline.base.Dates
import com.djavid.checksonline.model.entities.DateInterval
import com.djavid.checksonline.presenter.stats.StatsPresenter
import com.djavid.checksonline.presenter.stats.StatsView
import com.djavid.checksonline.view.adapters.StatsPagerAdapter
import kotlinx.android.synthetic.main.fragment_stats.*
import kotlinx.android.synthetic.main.toolbar_stats.*
import toothpick.Toothpick
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

    private var currentPosition = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, Toothpick.openScopes(activity, this))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) Toothpick.closeScope(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setPopupMenu()
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
