package com.sbcf.pillbox.utils

import android.content.Context
import com.sbcf.pillbox.R
import com.sbcf.pillbox.features.medicationreminders.data.DayOfWeek
import java.util.Calendar
import javax.inject.Inject

class DisplayFormatterImpl @Inject constructor(context: Context) : DisplayFormatter {
    private val daysOfWeek = context.resources.getStringArray(R.array.days_of_week)
    private val daysOfWeekShort = context.resources.getStringArray(R.array.days_of_week_short)

    override fun time(hour: Int, minute: Int): String {
        val fmtHour = padTwoDigitNumber(hour)
        val fmtMinute = padTwoDigitNumber(minute)

        return "$fmtHour:$fmtMinute"
    }

    override fun time(cal: Calendar): String {
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        return time(hour, minute)
    }

    override fun dayOfWeekAndDateTime(cal: Calendar): String {
        val dayOfWeek = dayOfWeek(DayOfWeek.fromCalendar(cal))
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)
        val day = padTwoDigitNumber(cal.get(Calendar.DAY_OF_MONTH))
        val month = padTwoDigitNumber(cal.get(Calendar.MONTH) + 1)

        return "$dayOfWeek $day.$month ${time(hour, minute)}"
    }

    override fun dayOfWeekAndDate(cal: Calendar): String {
        val dayOfWeek = dayOfWeek(DayOfWeek.fromCalendar(cal))
        val day = padTwoDigitNumber(cal.get(Calendar.DAY_OF_MONTH))
        val month = padTwoDigitNumber(cal.get(Calendar.MONTH) + 1)

        return "$dayOfWeek $day.$month"
    }

    override fun dayOfWeekAndFullDate(cal: Calendar): String {
        val dayOfWeek = dayOfWeek(DayOfWeek.fromCalendar(cal))
        val day = padTwoDigitNumber(cal.get(Calendar.DAY_OF_MONTH))
        val month = padTwoDigitNumber(cal.get(Calendar.MONTH) + 1)
        val year = padTwoDigitNumber(cal.get(Calendar.YEAR))

        return "$dayOfWeek $day.$month.$year"
    }

    override fun dayOfWeek(day: DayOfWeek): String = daysOfWeek[day.ordinal]
    override fun dayOfWeekShort(day: DayOfWeek): String = daysOfWeekShort[day.ordinal]

    private fun padTwoDigitNumber(num: Int) = num.toString().padStart(2, '0')
}