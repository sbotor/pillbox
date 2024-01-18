package com.sbcf.pillbox.features.medicationreminders.services

import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder

interface MedicationReminderPublisher {
    suspend fun publishAndCalculateNextTimestamp(reminder: MedicationReminder)
    fun ensureChannel()
}

