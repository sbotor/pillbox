package com.sbcf.pillbox.features.reminderhistory.data.repositories

import com.sbcf.pillbox.features.reminderhistory.data.ReminderHistoryEntry

interface ReminderHistoryRepository {
    suspend fun create(entry: ReminderHistoryEntry): Int
    suspend fun getAll(): List<ReminderHistoryEntry>
    suspend fun markAsViewed(id: Int)
    suspend fun markConfirmation(id: Int, value: Boolean)
    suspend fun get(id: Int): ReminderHistoryEntry?
    suspend fun getNewest(amount: Int) : List<ReminderHistoryEntry>
}