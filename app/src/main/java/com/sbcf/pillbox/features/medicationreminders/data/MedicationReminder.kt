package com.sbcf.pillbox.features.medicationreminders.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity
class MedicationReminder {
    // TODO: Make this immutable
    @PrimaryKey(autoGenerate = true)
    var id = 0L
    var title = ""
    var hour = 0
    var minute = 0
    var lastDeliveryTimestamp = 0L
    var scheduledTimestamp: Long? = null
    var nextDeliveryTimestamp: Long? = null
    var days = ReminderDayMask()

    private val isRepeating
        get() = !days.isEmpty()

    // TODO: A service checking if delivery problems occured would be nice
    val isEnabled
        get() = nextDeliveryTimestamp != null

    fun enable(deliveryTimestamp: Long) {
        nextDeliveryTimestamp = deliveryTimestamp
    }

    fun disable() {
        nextDeliveryTimestamp = null
    }

    fun getEarliestRepeatingTimestamp(now: Calendar): Long? {
        val nextDelivery = nextDeliveryTimestamp ?: return null
        val nowMs = now.timeInMillis
        if (nowMs < nextDelivery) {
            return nextDeliveryTimestamp
        }

        if (!isRepeating) {
            return null
        }

        return getRepeatingNextDayTimestamp(now)
    }

    fun getEarliestTimestamp(now: Calendar): Long? {
        val nextDelivery = nextDeliveryTimestamp ?: Long.MIN_VALUE
        val nowMs = now.timeInMillis
        if (nowMs <= nextDelivery) {
            return nextDeliveryTimestamp
        }

        val possibleToday = now.clone() as Calendar
        possibleToday.set(Calendar.HOUR_OF_DAY, hour)
        possibleToday.set(Calendar.MINUTE, minute)
        possibleToday.set(Calendar.SECOND, 0)
        possibleToday.set(Calendar.MILLISECOND, 0)

        if (possibleToday.timeInMillis >= nowMs) {
            return possibleToday.timeInMillis
        }

        if (isRepeating) {
            return getRepeatingNextDayTimestamp(now)
        }

        val nextDay = now.clone() as Calendar
        nextDay.set(Calendar.HOUR_OF_DAY, 0)
        nextDay.set(Calendar.MINUTE, 0)
        nextDay.set(Calendar.SECOND, 0)
        nextDay.set(Calendar.MILLISECOND, 0)
        nextDay.add(Calendar.DAY_OF_MONTH, 1)
        nextDay.set(Calendar.HOUR_OF_DAY, hour)
        nextDay.set(Calendar.MINUTE, minute)

        return nextDay.timeInMillis
    }

    private fun getRepeatingNextDayTimestamp(now: Calendar): Long? {
        val currentDayOfWeek = ReminderDay.fromCalendar(now)
        var nextDayOfWeek = currentDayOfWeek.getNextDay()
        var dayDiff = 1

        while (dayDiff < 7) {
            if (days.isSet(nextDayOfWeek)) {
                break
            }

            nextDayOfWeek = nextDayOfWeek.getNextDay()
            dayDiff++
        }

        if (dayDiff == 7) {
            return null
        }

        val nextTime = now.clone() as Calendar
        nextTime.add(Calendar.DAY_OF_MONTH, dayDiff)
        nextTime.set(Calendar.HOUR_OF_DAY, hour)
        nextTime.set(Calendar.MINUTE, minute)

        return nextTime.timeInMillis
    }
}