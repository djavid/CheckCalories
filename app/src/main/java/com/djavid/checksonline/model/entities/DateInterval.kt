package com.djavid.checksonline.model.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DateInterval(val interval: String, val dateStart: String, val dateEnd: String) : Parcelable