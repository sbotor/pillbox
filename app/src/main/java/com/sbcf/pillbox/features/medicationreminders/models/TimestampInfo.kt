package com.sbcf.pillbox.features.medicationreminders.models

import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder
import com.sbcf.pillbox.features.medicationreminders.data.DayOfWeekMask

data class TimestampInfo(
    val nextDeliveryTimestamp: Long?,
    val days: DayOfWeekMask,
    val hour: Int,
    val minute: Int
) {
    constructor(rem: MedicationReminder) : this(
        rem.nextDeliveryTimestamp,
        rem.days,
        rem.hour,
        rem.minute
    )

    constructor(rem: MedicationReminderOverview) : this(
        rem.nextDeliveryTimestamp,
        rem.days,
        rem.hour,
        rem.minute
    )
}