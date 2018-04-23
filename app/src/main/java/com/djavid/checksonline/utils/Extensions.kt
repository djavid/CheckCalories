package com.djavid.checksonline.utils

import android.util.Base64
import android.view.View
import com.djavid.checksonline.BuildConfig
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.io.UnsupportedEncodingException


fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun getAuthToken(): String {
    var data = ByteArray(0)

    try {
        data = (BuildConfig.FNS_USERNAME + ":" + BuildConfig.FNS_PASSWORD)
                .toByteArray(charset("UTF-8"))
    } catch (e: UnsupportedEncodingException) {
        e.printStackTrace()
    }

    return "Basic " + Base64.encodeToString(data, Base64.NO_WRAP)
}

fun parseTime(s: String?): String {
    val date = DateTime.parse(s)
    val formatter: DateTimeFormatter = DateTimeFormat.forPattern("HH:mm")
    return formatter.print(date)
}

fun parseDate(s: String?) : String {
    val date = DateTime.parse(s)
    val formatter: DateTimeFormatter = DateTimeFormat.forPattern("dd MMMMMMMM, HH:mm")
    return formatter.print(date)
}