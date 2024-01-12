package com.sbcf.pillbox.features.medications.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sbcf.pillbox.features.medications.models.MedicationOverview

@Dao
interface MedicationDao {
    @Query("SELECT id, name, dosage FROM Medication ORDER BY name")
    suspend fun getAll(): List<MedicationOverview>

    @Insert
    suspend fun insert(medication: Medication)
}