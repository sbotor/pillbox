package com.sbcf.pillbox.features.medicationreminders.models

import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder
import com.sbcf.pillbox.features.medicationreminders.data.ReminderDayMask

data class TimestampInfo(
    val nextDeliveryTimestamp: Long?,
    val days: ReminderDayMask,
    val hour: Int,
    val minute: Int
) {
    constructor(rem: MedicationReminder) : this(rem.id, rem.days, rem.hour, rem.minute)
    constructor(rem: MedicationReminderOverview) : this(rem.id, rem.days, rem.hour, rem.minute)
}