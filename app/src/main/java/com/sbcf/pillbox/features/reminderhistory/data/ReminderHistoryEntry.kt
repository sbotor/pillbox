package com.sbcf.pillbox.features.reminderhistory.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class ReminderHistoryEntryData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val deliveredTimestamp: Long,
    val description: String
)

@Entity(primaryKeys = ["entryId", "ordinal"])
data class ReminderHistoryItem(
    var ordinal: Int,
    var entryId: Int,
    val description: String
)

data class ReminderHistoryEntry(
    @Embedded
    val data: ReminderHistoryEntryData,

    @Relation(parentColumn = "id", entityColumn = "entryId")
    val items: List<ReminderHistoryItem>
)