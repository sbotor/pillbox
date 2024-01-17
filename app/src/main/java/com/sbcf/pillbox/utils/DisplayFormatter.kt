package com.sbcf.pillbox.utils

import com.sbcf.pillbox.features.medicationreminders.data.DayOfWeek
import java.util.Calendar

interface DisplayFormatter {
    fun time(hour: Int, minute: Int): String
    fun dayOfTheWeekAndTime(cal: Calendar): String
    fun dayOfWeek(day: DayOfWeek): String
    fun dayOfWeekShort(day: DayOfWeek): String
}