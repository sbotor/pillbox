package com.sbcf.pillbox.features.medicationreminders.data.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder
import com.sbcf.pillbox.features.medicationreminders.data.ReminderMedication
import com.sbcf.pillbox.features.medicationreminders.data.ReminderWithMedications
import com.sbcf.pillbox.features.medicationreminders.models.MedicationReminderOverview

@Dao
abstract class MedicationReminderDao {

    @Query("SELECT * FROM MedicationReminder WHERE nextDeliveryTimestamp != null")
    abstract suspend fun getAllEnabled(): List<MedicationReminder>

    @Query("SELECT * FROM MedicationReminder WHERE nextDeliveryTimestamp IS NOT null ORDER BY nextDeliveryTimestamp LIMIT 1")
    abstract suspend fun getNextEnabled(): MedicationReminder?

    @Update
    abstract suspend fun updateMany(reminders: List<MedicationReminder>)

    @Update
    abstract suspend fun update(reminder: MedicationReminder)

    @Query("SELECT * FROM MedicationReminder WHERE id = :id")
    abstract suspend fun getById(id: Int): MedicationReminder?

    @Query("SELECT id, hour, minute, nextDeliveryTimestamp, days, title FROM MedicationReminder ORDER BY hour, minute, title")
    abstract suspend fun getAll(): List<MedicationReminderOverview>

    @Query("UPDATE MedicationReminder SET nextDeliveryTimestamp = :value WHERE id = :id")
    abstract suspend fun changeDeliveryTimestamp(id: Int, value: Long?)

    @Query("UPDATE MedicationReminder SET scheduledTimestamp = null WHERE scheduledTimestamp != null")
    abstract suspend fun invalidateAll()

    @Insert
    abstract suspend fun add(reminder: MedicationReminder): Long

    @Query("DELETE FROM MedicationReminder WHERE id = :id")
    abstract suspend fun delete(id: Int)

    @Transaction
    @Query("SELECT * FROM MedicationReminder WHERE id = :id")
    abstract suspend fun getReminderWithMedications(id: Int): ReminderWithMedications?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateReminderMedications(rem: List<ReminderMedication>)

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateReminderWithMedications(rem: ReminderWithMedications) {
        val existing = getReminderWithMedications(rem.reminder.id)!!

        val idsToRemove = existing.medications.map { it.id }.toHashSet()
        val idsToAdd = hashSetOf<Int>()

        for (med in rem.medications) {
            if (idsToRemove.remove(med.id)) {
                continue
            }

            idsToAdd.add(med.id)
        }

        update(rem.reminder)

        if (idsToAdd.isNotEmpty()) {
            addReminderMedications(idsToAdd.map { ReminderMedication(rem.reminder.id, it) })
        }

        if (idsToRemove.isNotEmpty()) {
            removeReminderMedications(rem.reminder.id, idsToRemove.toList())
        }
    }

    @Transaction
    @Insert
    suspend fun addReminderWithMedications(rem: ReminderWithMedications): Int {
        val remId = add(rem.reminder).toInt()

        val remMeds = rem.medications.map { ReminderMedication(remId, it.id) }
        addReminderMedications(remMeds)

        return remId
    }

    @Insert
    abstract suspend fun addReminderMedications(rem: List<ReminderMedication>)

    @Query("DELETE FROM ReminderMedication WHERE reminderId = :reminderId AND medicationId IN (:medIds)")
    abstract suspend fun removeReminderMedications(reminderId: Int, medIds: List<Int>)
}