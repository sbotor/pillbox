package com.sbcf.pillbox.features.medicationreminders.services

import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder

interface MedicationReminderPublisher {
    fun publish(reminder: MedicationReminder)
    fun ensureChannel()
}
