package com.rlm.imeikotlin.repository.local.converter

import android.text.format.DateFormat
import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun toDate(timestamp: Long?) = timestamp?.let { Date(it) }

        @TypeConverter
        @JvmStatic
        fun toTimestamp(date: Date?) = date?.time

        @TypeConverter
        @JvmStatic
        fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

        @TypeConverter
        @JvmStatic
        fun datestampToCalendar(value: Long): Calendar =
            Calendar.getInstance().apply { timeInMillis = value }

        fun getDayMonth(date: Date): String {
            val day = DateFormat.format("dd", date) as String // 20
            val monthString = DateFormat.format("MMM", date) as String // Jun
            return day + monthString
        }
    }
}