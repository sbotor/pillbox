package com.sbcf.pillbox.features.medicationreminders.models

data class MedicationReminderOverview(
    val id: Int,
    val hour: Int,
    val minute: Int,
    val isEnabled: Boolean
)