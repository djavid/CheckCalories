package com.djavid.checksonline.features.checks

import android.content.Context
import android.widget.TextView
import com.djavid.checksonline.R
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Position
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import org.joda.time.DateTime

@Layout(R.layout.layout_date_item)
class DateItem (
        private val context: Context?,
        private val dateTime: DateTime
) {

    @View(R.id.tv_date)
    lateinit var tv_date: TextView

    @JvmField
    @Position
    var position: Int = 0

    @Resolve
    fun onResolved() {
//        val prettyTime = PrettyTime()
//        tv_date.text = prettyTime.format(dateTime.toDate())

        val date = dateTime.dayOfMonth().asText + " " + dateTime.monthOfYear().asText
        tv_date.text = date
    }

}