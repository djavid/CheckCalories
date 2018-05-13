package com.djavid.checksonline.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Base64
import android.view.View
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.djavid.checksonline.BuildConfig
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.io.UnsupportedEncodingException
import java.util.regex.Matcher
import java.util.regex.Pattern
import android.util.DisplayMetrics



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