package com.sbcf.pillbox.features.medicationreminders.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity
class MedicationReminder {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var title = ""
    var hour = 0
    var minute = 0
    var lastDeliveryTimestamp = 0L
    var scheduledTimestamp: Long? = null
    var days = ReminderDayMask()
    var nextDeliveryTimestamp: Long? = 0L

    private val isRepeating
        get() = !days.isEmpty()

    val isEnabled
        get() = nextDeliveryTimestamp != null

    fun enable(deliveryTimestamp: Long) {
        nextDeliveryTimestamp = deliveryTimestamp
    }

    fun disable() {
        nextDeliveryTimestamp = null
    }

    fun getEarliestNextTimestamp(now: Calendar): Long? {
        val nextDelivery = nextDeliveryTimestamp ?: return null
        val nowMs = now.timeInMillis
        if (nowMs < nextDelivery) {
            return nextDeliveryTimestamp
        }

        if (!isRepeating) {
            return null
        }

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