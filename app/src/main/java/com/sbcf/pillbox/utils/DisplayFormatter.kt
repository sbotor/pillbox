package com.sbcf.pillbox.utils

import com.sbcf.pillbox.features.medicationreminders.data.DayOfWeek
import java.util.Calendar

interface DisplayFormatter {
    fun time(hour: Int, minute: Int): String
    fun time(cal: Calendar): String
    fun dayOfWeekAndDateTime(cal: Calendar): String
    fun dayOfWeekAndDate(cal: Calendar): String
    fun dayOfWeekAndFullDate(cal: Calendar): String
    fun dayOfWeek(day: DayOfWeek): String
    fun dayOfWeekShort(day: DayOfWeek): String
}