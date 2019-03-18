package com.djavid.checksonline.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Base64
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import com.djavid.checksonline.BuildConfig
import com.djavid.checksonline.model.entities.DateInterval
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.io.UnsupportedEncodingException
import java.util.regex.Matcher
import java.util.regex.Pattern


fun View.show(visible: Boolean) {
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

fun String.parseTime(): String {
    val date = DateTime.parse(this)
    val formatter: DateTimeFormatter = DateTimeFormat.forPattern("HH:mm")
    return formatter.print(date)
}

fun String.parseDate() : String {
    val date = DateTime.parse(this)
    val formatter: DateTimeFormatter = DateTimeFormat.forPattern("dd MMMMMMMM, HH:mm")
    return formatter.print(date)
}

fun playBeepSound() {
    val tg = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100)
    tg.startTone(ToneGenerator.TONE_PROP_BEEP)
}

fun Context?.vibrate() {
    this ?: return

    val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
    else
        v.vibrate(100)
}

fun String.removeLeadingZeros(): String {
    return this.replaceFirst("""^0+(?!$)""".toRegex(), "")
}

fun String.getCheckMatcher(): Matcher {
    val pattern = "t=(\\d+T\\d+)&s=(\\d+\\.\\d+)&fn=(\\d+)&i=(\\d+)&fp=(\\d+)&n=(\\d+)"
    val r = Pattern.compile(pattern)

    return r.matcher(this)
}

fun Context.pxToDp(px: Int): Int {
    val displayMetrics = resources.displayMetrics
    return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}

fun Context.dpToPx(dp: Int): Int {
    val displayMetrics = resources.displayMetrics
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}

fun Context.spToPx(sp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics)
}

fun String.formatShopTitle() : String {
    var s = this
    s = """(ООО|ЗАО|ооо|зао)""".toRegex().replace(s, "")
    s = """(['"_])""".toRegex().replace(s, "")
    s = """\s+""".toRegex().replace(s, " ")
    s = s.trim()

    return s
}

fun DateInterval.getSpannable() : SpannableString {

    val dateStart = DateTime.parse(this.dateStart)
    val dateEnd = DateTime.parse(this.dateEnd)

    var days = -1
    try {
        days = this.interval.toInt()
    } catch (throwable: Throwable) {
        if (throwable !is NumberFormatException)
            throwable.printStackTrace()
    }

    var s = ""

    if (days > 0) {
        s = dateStart.dayOfMonth().asString + "-" + dateEnd.dayOfMonth().asString +
                "\n" + dateStart.monthOfYear().asText
    } else {
        when (this.interval) {
            "day" -> s = dateStart.dayOfMonth().asString + "\n" + dateStart.monthOfYear().asText
            "week"-> s = dateStart.dayOfMonth().asString + "-" + dateEnd.dayOfMonth().asString +
                    "\n" + dateStart.monthOfYear().asText
            "month"-> s = Config.months[dateStart.monthOfYear - 1]
        }
    }

    val spannable = SpannableString(s)

    if (s.contains("\n")) {
        val divider = s.indexOf("\n")

        spannable.setSpan(StyleSpan(Typeface.BOLD), 0, divider, 0)
        spannable.setSpan(ForegroundColorSpan(Color.parseColor("#979797")), divider, s.length, 0)
        spannable.setSpan(RelativeSizeSpan(.7f), divider, s.length, 0)
    } else {
        spannable.setSpan(StyleSpan(Typeface.BOLD), 0, s.length, 0)
    }

    return spannable

}