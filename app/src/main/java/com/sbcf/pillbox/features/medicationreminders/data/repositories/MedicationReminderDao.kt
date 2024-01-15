package com.sbcf.pillbox.features.medicationreminders.data.repositories

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder
import com.sbcf.pillbox.features.medicationreminders.models.MedicationReminderOverview

@Dao
interface MedicationReminderDao {

    @Query("SELECT * FROM MedicationReminder WHERE isEnabled AND lastScheduleTimestamp < :scheduledBefore")
    suspend fun getEnabledScheduledBefore(scheduledBefore: Long): List<MedicationReminder>

    @Update
    suspend fun updateMany(reminders: List<MedicationReminder>)

    @Update
    suspend fun update(reminder: MedicationReminder)

    @Query("SELECT * FROM MedicationReminder WHERE id = :id")
    suspend fun getById(id: Int): MedicationReminder?

    @Query("SELECT id, hour, minute, isEnabled FROM MedicationReminder ORDER BY title")
    suspend fun getAll(): List<MedicationReminderOverview>

    @Query("UPDATE MedicationReminder SET isEnabled = :value WHERE id = :id")
    suspend fun changeStatus(id: Int, value: Boolean)
}