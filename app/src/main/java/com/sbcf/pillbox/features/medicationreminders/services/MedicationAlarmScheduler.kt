package com.sbcf.pillbox.features.medicationreminders.services

import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder

interface MedicationAlarmScheduler {
    fun scheduleAll(reminders: List<MedicationReminder>)
    fun schedule(reminder: MedicationReminder)
    fun unschedule(reminder: MedicationReminder)
}
