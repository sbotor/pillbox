package com.sbcf.pillbox.features.reminderhistory.data.repositories

import com.sbcf.pillbox.features.reminderhistory.data.ReminderHistoryEntry
import javax.inject.Inject

class ReminderHistoryRepositoryImpl @Inject constructor(private val dao: ReminderHistoryDao) :
    ReminderHistoryRepository {
    override suspend fun create(entry: ReminderHistoryEntry): Int = dao.createEntry(entry)

    override suspend fun getAll(): List<ReminderHistoryEntry> = dao.getAll()
}