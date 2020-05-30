package com.essa.colorweather.ui

import android.text.format.DateFormat
import java.util.*

public fun convertTime(time: Int, format: String): String {
    val calendar =  Calendar.getInstance(Locale.getDefault())
    calendar.timeInMillis = (time * 1000L)
    return DateFormat.format(format, calendar).toString().capitalize()
}