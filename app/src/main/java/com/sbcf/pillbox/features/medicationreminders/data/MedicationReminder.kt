package com.sbcf.pillbox.features.medicationreminders.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MedicationReminder(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val hour: Int,
    val minute: Int,
    var lastScheduleTimestamp: Long,
    var lastDeliveredTimestamp: Long,
    var isEnabled: Boolean
)