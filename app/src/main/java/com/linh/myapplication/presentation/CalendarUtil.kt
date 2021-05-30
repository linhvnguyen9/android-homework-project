package com.linh.myapplication.presentation

import java.sql.Timestamp
import java.util.*

object CalendarUtl {
    fun toFormattedString(calendar: Calendar): String =
        "${calendar.get(Calendar.DATE)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"

    fun toFormattedString(timestamp: Long): String {
        val calendar = Calendar.getInstance().apply { timeInMillis = timestamp }
        return "${calendar.get(Calendar.DATE)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
    }
}