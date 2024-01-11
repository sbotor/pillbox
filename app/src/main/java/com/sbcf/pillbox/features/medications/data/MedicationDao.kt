package com.sbcf.pillbox.features.medications.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MedicationDao {
    @Query("SELECT * FROM Medication ORDER BY name")
    suspend fun getAll(): List<Medication>

    @Insert
    suspend fun insert(medication: Medication)
}