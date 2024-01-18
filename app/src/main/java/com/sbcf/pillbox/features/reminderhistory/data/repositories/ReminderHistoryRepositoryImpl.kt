package com.sbcf.pillbox.features.reminderhistory.data.repositories

import com.sbcf.pillbox.features.reminderhistory.data.ReminderHistoryEntry
import javax.inject.Inject

class ReminderHistoryRepositoryImpl @Inject constructor(private val dao: ReminderHistoryDao) :
    ReminderHistoryRepository {
    override suspend fun create(entry: ReminderHistoryEntry): Int = dao.createEntry(entry)
    override suspend fun getAll(): List<ReminderHistoryEntry> = dao.getAll()
    override suspend fun markAsViewed(id: Int) = dao.markAsViewed(id)
    override suspend fun markConfirmation(id: Int, value: Boolean) = dao.markConfirmation(id, value)
    override suspend fun get(id: Int): ReminderHistoryEntry? = dao.get(id)
}