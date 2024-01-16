package com.sbcf.pillbox.features.medicationreminders.data.repositories

import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder
import com.sbcf.pillbox.features.medicationreminders.models.MedicationReminderOverview

interface MedicationRemindersRepository {
    suspend fun invalidateAllAndGetEnabled(): List<MedicationReminder>
    suspend fun updateMany(reminders: List<MedicationReminder>)
    suspend fun get(id: Long): MedicationReminder?
    suspend fun update(reminder: MedicationReminder)
    suspend fun getAll(): List<MedicationReminderOverview>
    suspend fun changeDeliveryTimestamp(id: Long, value: Long?)
    suspend fun add(reminder: MedicationReminder): Long
    suspend fun deleteById(id: Long)
}

