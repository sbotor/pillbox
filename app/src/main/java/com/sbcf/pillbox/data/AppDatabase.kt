package com.sbcf.pillbox.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder
import com.sbcf.pillbox.features.medicationreminders.data.ReminderMedication
import com.sbcf.pillbox.features.medications.data.Medication
import com.sbcf.pillbox.features.medications.data.repositories.MedicationDao
import com.sbcf.pillbox.features.medicationreminders.data.repositories.MedicationReminderDao
import com.sbcf.pillbox.features.reminderhistory.data.ReminderHistoryEntryData
import com.sbcf.pillbox.features.reminderhistory.data.ReminderHistoryItem
import com.sbcf.pillbox.features.reminderhistory.data.repositories.ReminderHistoryDao

@Database(
    entities = [
        Medication::class,
        MedicationReminder::class,
        ReminderHistoryEntryData::class,
        ReminderHistoryItem::class,
        ReminderMedication::class
    ],
    version = AppDatabase.VERSION
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DEFAULT_NAME = "database"
        const val VERSION = 1
    }

    abstract fun medicationDao(): MedicationDao
    abstract fun medicationReminderDao(): MedicationReminderDao
    abstract fun medicationReminderHistoryDao(): ReminderHistoryDao
}
