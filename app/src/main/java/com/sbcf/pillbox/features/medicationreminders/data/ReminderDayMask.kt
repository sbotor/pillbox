package com.sbcf.pillbox.features.medicationreminders.data

class ReminderDayMask(mask: Int = 0) {
    var value = mask and 0b01111111
        private set

    fun set(day: ReminderDay) {
        value = value or day.value
    }

    fun set(idx: Int, value: Boolean) {
        if (idx >= ReminderDay.days.size) {
            return
        }

        val day = ReminderDay.days[idx]
        if (value) {
            set(day)
        } else {
            reset(day)
        }
    }

    fun reset(day: ReminderDay) {
        value = value and day.value.inv()
    }

    fun isSet(day: ReminderDay) = (value and day.value) > 0

    fun isEmpty() = value == 0

    fun getSetDays(): List<ReminderDay> {
        return ReminderDay.days.filter { isSet(it) }
    }
}