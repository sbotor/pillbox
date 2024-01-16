package com.sbcf.pillbox.features.medicationreminders.data

import java.util.Calendar

enum class DayOfWeek(val value: Int) {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(4),
    THURSDAY(8),
    FRIDAY(16),
    SATURDAY(32),
    SUNDAY(64);

    fun getNextDay(): DayOfWeek {
        var idx = ordinal
        if (idx == SUNDAY.ordinal) {
            idx = MONDAY.ordinal
        } else {
            idx += 1
        }

        return days[idx]
    }

    companion object {
        fun fromCalendar(calendar: Calendar): DayOfWeek {
            return when (calendar.get(Calendar.DAY_OF_WEEK)) {
                Calendar.MONDAY -> MONDAY
                Calendar.TUESDAY -> TUESDAY
                Calendar.WEDNESDAY -> WEDNESDAY
                Calendar.THURSDAY -> THURSDAY
                Calendar.FRIDAY -> FRIDAY
                Calendar.SUNDAY -> SUNDAY
                Calendar.SATURDAY -> SATURDAY
                else -> throw IllegalArgumentException()
            }
        }

        val days = listOf(
            MONDAY,
            TUESDAY,
            WEDNESDAY,
            THURSDAY,
            FRIDAY,
            SATURDAY,
            SUNDAY
        )
    }
}