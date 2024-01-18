package com.sbcf.pillbox.features.medicationreminders.services

import com.sbcf.pillbox.features.medicationreminders.data.ReminderWithMedications

interface MedicationReminderPublisher {
    suspend fun publishAndCalculateNextTimestamp(reminder: ReminderWithMedications)
    fun ensureChannel()
}

