package com.sbcf.pillbox.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sbcf.pillbox.features.medications.data.Medication
import com.sbcf.pillbox.features.medications.data.MedicationDao

@Database(entities = [Medication::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicationDao(): MedicationDao
}