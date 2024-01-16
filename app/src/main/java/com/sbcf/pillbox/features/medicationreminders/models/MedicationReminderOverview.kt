package com.sbcf.pillbox.features.medicationreminders.models

import com.sbcf.pillbox.features.medicationreminders.data.ReminderDayMask

data class MedicationReminderOverview(
    val id: Long,
    val hour: Int,
    val minute: Int,
    val nextDeliveryTimestamp: Long?,
    val days: ReminderDayMask,
    val title: String
) {
    val isEnabled
        get() = nextDeliveryTimestamp != null
}