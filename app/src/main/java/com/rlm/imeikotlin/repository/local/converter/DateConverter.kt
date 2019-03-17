package com.rlm.imeikotlin.repository.local.converter

import android.text.format.DateFormat
import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long?) = timestamp?.let{ Date(it) }

    @TypeConverter
    fun toTimestamp(date: Date?) = date?.time

    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }

    fun getDayMonth(date: Date): String {
        val day = DateFormat.format("dd", date) as String // 20
        val monthString = DateFormat.format("MMM", date) as String // Jun
        return day + monthString
    }
}