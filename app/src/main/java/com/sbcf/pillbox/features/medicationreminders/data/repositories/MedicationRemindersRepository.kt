package com.sbcf.pillbox.features.medicationreminders.data.repositories

import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder
import com.sbcf.pillbox.features.medicationreminders.data.ReminderMedication
import com.sbcf.pillbox.features.medicationreminders.data.ReminderWithMedications
import com.sbcf.pillbox.features.medicationreminders.models.MedicationReminderOverview

interface MedicationRemindersRepository {
    suspend fun invalidateAllAndGetEnabled(): List<MedicationReminder>
    suspend fun updateMany(reminders: List<MedicationReminder>)
    suspend fun get(id: Int): MedicationReminder?
    suspend fun update(reminder: MedicationReminder)
    suspend fun getAll(): List<MedicationReminderOverview>
    suspend fun getNext(): MedicationReminder?
    suspend fun changeDeliveryTimestamp(id: Int, value: Long?)
    suspend fun add(reminder: MedicationReminder): Int
    suspend fun deleteById(id: Int)
    suspend fun getReminderWithMedications(id: Int): ReminderWithMedications?
    suspend fun updateReminderWithMedications(rem: ReminderWithMedications)
    suspend fun addReminderWithMedications(rem: ReminderWithMedications): Int
}

