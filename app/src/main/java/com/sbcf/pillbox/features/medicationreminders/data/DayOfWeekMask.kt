package com.sbcf.pillbox.features.medicationreminders.data

class DayOfWeekMask(mask: Int = 0) {
    var value = mask and 0b01111111
        private set

    fun set(day: DayOfWeek) {
        value = value or day.value
    }

    fun set(idx: Int, value: Boolean) {
        if (idx >= DayOfWeek.days.size) {
            return
        }

        val day = DayOfWeek.days[idx]
        if (value) {
            set(day)
        } else {
            reset(day)
        }
    }

    fun reset(day: DayOfWeek) {
        value = value and day.value.inv()
    }

    fun isSet(day: DayOfWeek) = (value and day.value) > 0

    fun isSet(idx: Int): Boolean {
        if (idx >= DayOfWeek.days.size) {
            return false
        }

        return isSet(DayOfWeek.days[idx])
    }

    fun isEmpty() = value == 0
    fun isNotEmpty() = value > 0

    fun getSetDays(): List<DayOfWeek> {
        return DayOfWeek.days.filter { isSet(it) }
    }
}