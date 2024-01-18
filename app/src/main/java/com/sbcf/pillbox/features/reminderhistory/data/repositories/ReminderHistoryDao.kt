package com.sbcf.pillbox.features.reminderhistory.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.sbcf.pillbox.features.reminderhistory.data.ReminderHistoryEntry
import com.sbcf.pillbox.features.reminderhistory.data.ReminderHistoryEntryData
import com.sbcf.pillbox.features.reminderhistory.data.ReminderHistoryItem

@Dao
abstract class ReminderHistoryDao {
    @Insert
    abstract suspend fun createData(data: ReminderHistoryEntryData): Long

    @Insert
    abstract suspend fun createItems(items: List<ReminderHistoryItem>)

    @Transaction
    @Insert
    suspend fun createEntry(entry: ReminderHistoryEntry): Int {
        val id = createData(entry.data).toInt()

        entry.items.forEachIndexed { i, x ->
            x.ordinal = i
            x.entryId = id
        }
        createItems(entry.items)

        return id
    }

    @Query("SELECT * FROM ReminderHistoryEntryData ORDER BY deliveredTimestamp DESC LIMIT :amount")
    abstract suspend fun getNewest(amount: Int): List<ReminderHistoryEntry>

    @Query("SELECT * FROM ReminderHistoryEntryData")
    @Transaction
    abstract suspend fun getAll(): List<ReminderHistoryEntry>

    @Query("UPDATE ReminderHistoryEntryData SET wasViewed = 1 WHERE id = :id")
    abstract suspend fun markAsViewed(id: Int)

    @Query("UPDATE ReminderHistoryEntryData SET isConfirmed = :value WHERE id = :id")
    abstract suspend fun markConfirmation(id: Int, value: Boolean)

    @Query("SELECT * FROM ReminderHistoryEntryData WHERE id = :id")
    abstract suspend fun get(id: Int): ReminderHistoryEntry?
}