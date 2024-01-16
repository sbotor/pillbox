package com.sbcf.pillbox.features.medicationreminders.data

import androidx.room.Entity
import androidx.room.PrimaryKey

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
    var days = DayOfWeekMask()

    // TODO: A service checking if delivery problems occured would be nice
    val isEnabled
        get() = nextDeliveryTimestamp != null

    fun enable(deliveryTimestamp: Long) {
        nextDeliveryTimestamp = deliveryTimestamp
    }

    fun disable() {
        nextDeliveryTimestamp = null
    }
}