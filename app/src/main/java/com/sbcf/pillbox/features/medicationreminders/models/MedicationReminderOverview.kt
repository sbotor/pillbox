package com.sbcf.pillbox.features.medicationreminders.models

import com.sbcf.pillbox.features.medicationreminders.data.DayOfWeekMask

data class MedicationReminderOverview(
    val id: Int,
    val hour: Int,
    val minute: Int,
    val nextDeliveryTimestamp: Long?,
    val days: DayOfWeekMask,
    val title: String
) {
    val isEnabled
        get() = nextDeliveryTimestamp != null
}