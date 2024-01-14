package com.sbcf.pillbox.features.medications.data

interface MedicationNotificationsRepository {
    suspend fun getDue(scheduledBefore: Long): List<MedicationNotification>
    suspend fun updateMany(notifications: List<MedicationNotification>)
    suspend fun get(id: Int): MedicationNotification?
    suspend fun update(notification: MedicationNotification)
}