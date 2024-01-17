package com.sbcf.pillbox.features.medicationreminders.data.repositories

import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder
import com.sbcf.pillbox.features.medicationreminders.models.MedicationReminderOverview

interface MedicationRemindersRepository {
    suspend fun invalidateAllAndGetEnabled(): List<MedicationReminder>
    suspend fun updateMany(reminders: List<MedicationReminder>)
    suspend fun get(id: Int): MedicationReminder?
    suspend fun update(reminder: MedicationReminder)
    suspend fun getAll(): List<MedicationReminderOverview>
    suspend fun changeDeliveryTimestamp(id: Int, value: Long?)
    suspend fun add(reminder: MedicationReminder): Int
    suspend fun deleteById(id: Int)
}

