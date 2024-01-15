package com.sbcf.pillbox.features.medicationreminders.data.repositories

import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder
import com.sbcf.pillbox.features.medicationreminders.models.MedicationReminderOverview

interface MedicationRemindersRepository {
    suspend fun getDue(scheduledBefore: Long): List<MedicationReminder>
    suspend fun updateMany(reminders: List<MedicationReminder>)
    suspend fun get(id: Int): MedicationReminder?
    suspend fun update(reminder: MedicationReminder)
    suspend fun getAll(): List<MedicationReminderOverview>
    suspend fun changeStatus(id: Int, isEnabled: Boolean)
}

