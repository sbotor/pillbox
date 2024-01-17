package com.sbcf.pillbox.utils

import android.content.Context
import com.sbcf.pillbox.R
import com.sbcf.pillbox.features.medicationreminders.data.DayOfWeek
import java.util.Calendar
import javax.inject.Inject

class DisplayFormatterImpl @Inject constructor(private val context: Context) : DisplayFormatter {
    private val daysOfWeek = context.resources.getStringArray(R.array.days_of_week)
    private val daysOfWeekShort = context.resources.getStringArray(R.array.days_of_week_short)

    override fun time(hour: Int, minute: Int): String {
        val fmtHour = hour.toString().padStart(2, '0')
        val fmtMinute = minute.toString().padStart(2, '0')

        return "$fmtHour:$fmtMinute"
    }

    override fun dayOfTheWeekAndTime(cal: Calendar): String {
        val day = dayOfWeek(DayOfWeek.fromCalendar(cal))
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        return "$day ${time(hour, minute)}"
    }

    override fun dayOfWeek(day: DayOfWeek): String = daysOfWeek[day.ordinal]

    override fun dayOfWeekShort(day: DayOfWeek): String = daysOfWeekShort[day.ordinal]
}