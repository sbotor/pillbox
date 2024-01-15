package com.sbcf.pillbox.features.medicationreminders.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder
import com.sbcf.pillbox.features.medicationreminders.models.MedicationReminderOverview

@Dao
interface MedicationReminderDao {

    @Query("SELECT * FROM MedicationReminder WHERE nextDeliveryTimestamp != null")
    suspend fun getAllEnabled(): List<MedicationReminder>

    @Update
    suspend fun updateMany(reminders: List<MedicationReminder>)

    @Update
    suspend fun update(reminder: MedicationReminder)

    @Query("SELECT * FROM MedicationReminder WHERE id = :id")
    suspend fun getById(id: Long): MedicationReminder?

    @Query("SELECT id, hour, minute, nextDeliveryTimestamp, days, title FROM MedicationReminder ORDER BY title")
    suspend fun getAll(): List<MedicationReminderOverview>

    @Query("UPDATE MedicationReminder SET nextDeliveryTimestamp = :value WHERE id = :id")
    suspend fun changeDeliveryTimestamp(id: Long, value: Long?)

    @Query("UPDATE MedicationReminder SET scheduledTimestamp = null WHERE scheduledTimestamp != null")
    suspend fun invalidateAll()

    @Insert
    suspend fun add(reminder: MedicationReminder): Long
}