package com.sbcf.pillbox.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sbcf.pillbox.features.medications.data.Medication
import com.sbcf.pillbox.features.medications.data.repositories.MedicationDao

@Database(entities = [Medication::class], version = AppDatabase.VERSION)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DEFAULT_NAME = "database"
        const val VERSION = 1
    }

    abstract fun medicationDao(): MedicationDao
}
