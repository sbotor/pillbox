package com.sbcf.pillbox.features.medicationreminders.data

import java.util.Calendar

enum class ReminderDay(val value: Int, private val dayOrdinal: Int) {
    MONDAY(1, 1),
    TUESDAY(2, 2),
    WEDNESDAY(4, 3),
    THURSDAY(8, 4),
    FRIDAY(16, 5),
    SATURDAY(32, 6),
    SUNDAY(6, 7);

    private val days by lazy {
        listOf(
            MONDAY,
            TUESDAY,
            WEDNESDAY,
            THURSDAY,
            FRIDAY,
            SATURDAY,
            SUNDAY
        )
    }

    fun getNextDay(): ReminderDay {
        var ordinal = dayOrdinal
        if (ordinal == SUNDAY.dayOrdinal) {
            ordinal = MONDAY.dayOrdinal
        }

        return days[ordinal - 1]
    }

    companion object {
        fun fromCalendar(calendar: Calendar): ReminderDay {
            return when (calendar.get(Calendar.DAY_OF_WEEK)) {
                Calendar.MONDAY -> MONDAY;
                Calendar.TUESDAY -> TUESDAY;
                Calendar.WEDNESDAY -> WEDNESDAY;
                Calendar.THURSDAY -> THURSDAY;
                Calendar.FRIDAY -> FRIDAY;
                Calendar.SUNDAY -> SUNDAY;
                Calendar.SATURDAY -> SATURDAY;
                else -> throw IllegalArgumentException()
            }
        }
    }
}