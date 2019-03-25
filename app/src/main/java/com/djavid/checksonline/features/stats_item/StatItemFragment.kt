package com.djavid.checksonline.features.stats_item

import android.os.Bundle
import android.view.View
import com.djavid.checksonline.R
import com.djavid.checksonline.features.base.NewBaseFragment
import com.djavid.checksonline.model.entities.DateInterval
import javax.inject.Inject

class StatItemFragment : NewBaseFragment() {

    companion object {

        private const val ARG_INTERVAL = "interval"
        private const val ARG_DATE_START = "date_start"
        private const val ARG_DATE_END = "date_end"

        fun newInstance(interval: DateInterval): StatItemFragment {

            val bundle = Bundle()
            bundle.putString(ARG_INTERVAL, interval.interval)
            bundle.putString(ARG_DATE_START, interval.dateStart)
            bundle.putString(ARG_DATE_END, interval.dateEnd)

            return StatItemFragment().apply { arguments = bundle }
        }
    }

    @Inject
    lateinit var presenter: StatsItemContract.Presenter

    override val layoutResId = R.layout.fragment_stat_item


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        arguments?.getParcelable<DateInterval>(EXTRA_DATE_INTERVAL)?.let {
//            presenter.init(it)
//        }
        val interval = DateInterval(
                arguments?.getString(ARG_INTERVAL) ?: "",
                arguments?.getString(ARG_DATE_START) ?: "",
                arguments?.getString(ARG_DATE_END) ?: ""
        )
        presenter.init(interval)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }
}
