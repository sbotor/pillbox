package com.sbcf.pillbox.utils

import android.content.Context
import com.sbcf.pillbox.R
import com.sbcf.pillbox.features.medicationreminders.data.DayOfWeek
import java.util.Calendar
import javax.inject.Inject

class DisplayFormatterImpl @Inject constructor(private val context: Context) : DisplayFormatter {
    private val daysOfWeek = listOf(
        context.getString(R.string.monday),
        context.getString(R.string.tuesday),
        context.getString(R.string.wednesday),
        context.getString(R.string.thursday),
        context.getString(R.string.friday),
        context.getString(R.string.saturday),
        context.getString(R.string.sunday)
    )
    private val daysOfWeekShort = listOf(
        context.getString(R.string.monday_short),
        context.getString(R.string.tuesday_short),
        context.getString(R.string.wednesday_short),
        context.getString(R.string.thursday_short),
        context.getString(R.string.friday_short),
        context.getString(R.string.saturday_short),
        context.getString(R.string.sunday_short)
    )

    override fun time(hour: Int, minute: Int): String {
        val fmtHour = hour.toString().padStart(2, '0')
        val fmtMinute = minute.toString().padStart(2, '0')

        return "$fmtHour:$fmtMinute"
    }

    override fun dateTime(cal: Calendar): String {
        val day = dayOfWeek(DayOfWeek.fromCalendar(cal))
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        return "$day ${time(hour, minute)}"
    }

    override fun dayOfWeek(day: DayOfWeek): String = daysOfWeek[day.ordinal]

    override fun dayOfWeekShort(day: DayOfWeek): String = daysOfWeekShort[day.ordinal]
}