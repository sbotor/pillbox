package com.sbcf.pillbox.features.medicationreminders.data

class ReminderDayMask(mask: Int = 0) {
    var value = mask and 0b01111111
        private set

    fun set(day: ReminderDay) {
        value = value or day.value
    }

    fun unset(day: ReminderDay) {
        value = value and day.value.inv()
    }

    fun isSet(day: ReminderDay) = (value and day.value) > 0

    fun isEmpty() = value == 0
}