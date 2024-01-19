package com.sbcf.pillbox.features.medicationreminders.models

import com.sbcf.pillbox.features.medications.models.DosageUnit

data class ReminderMedicationItem(
    val id: Int,
    val name: String,
    val dosageAmount: Int,
    val dosageUnit: DosageUnit
)