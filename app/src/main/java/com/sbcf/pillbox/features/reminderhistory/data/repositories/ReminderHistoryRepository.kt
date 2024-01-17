package com.sbcf.pillbox.features.reminderhistory.data.repositories

import com.sbcf.pillbox.features.reminderhistory.data.ReminderHistoryEntry

interface ReminderHistoryRepository {
    suspend fun create(entry: ReminderHistoryEntry): Int
    suspend fun getAll(): List<ReminderHistoryEntry>
}