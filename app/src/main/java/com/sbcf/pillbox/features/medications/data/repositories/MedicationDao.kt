package com.sbcf.pillbox.features.medications.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sbcf.pillbox.features.medications.data.Medication
import com.sbcf.pillbox.features.medications.models.MedicationOverview

@Dao
interface MedicationDao {
    @Query("SELECT id, name, amount, unit, interval, intervalType FROM Medication ORDER BY name")
    suspend fun getAll(): List<MedicationOverview>

    @Query("SELECT id, name, amount, unit, interval, intervalType FROM Medication WHERE id NOT IN (:ids) ORDER BY name")
    suspend fun getAllExcept(ids: List<Int>): List<MedicationOverview>

    @Insert
    suspend fun insert(medication: Medication)

    @Query("SELECT * FROM Medication WHERE id = :id")
    suspend fun get(id: Int): Medication?

    @Update
    suspend fun update(medication: Medication)

    @Query("DELETE FROM Medication WHERE id = :medicationId")
    suspend fun delete(medicationId: Int)
}