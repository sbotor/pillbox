package com.sbcf.pillbox.features.medications.data

data class MedicationNotification(
    val id: Int = 0,
    val title: String,
    val hour: Int,
    val minute: Int,
    var lastScheduleTimestamp: Long,
    var lastDeliveredTimestamp: Long
)