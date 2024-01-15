package com.sbcf.pillbox.features.medicationreminders.services

import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder

interface MedicationAlarmScheduler {
    suspend fun scheduleAll(notifications: List<MedicationReminder>)
}
