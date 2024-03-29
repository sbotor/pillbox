package com.sbcf.pillbox.features.reminderhistory.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.sbcf.pillbox.features.medications.models.DosageUnit

@Entity
data class ReminderHistoryEntryData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val deliveredTimestamp: Long,
    val description: String,
    val wasViewed: Boolean = false,
    val isConfirmed: Boolean = false
)

@Entity(primaryKeys = ["entryId", "ordinal"])
data class ReminderHistoryItem(
    var ordinal: Int,
    var entryId: Int,
    val description: String,
    var dosageAmount: Int,
    var dosageUnit: DosageUnit,
)

data class ReminderHistoryEntry(
    @Embedded
    val data: ReminderHistoryEntryData,

    @Relation(parentColumn = "id", entityColumn = "entryId")
    val items: List<ReminderHistoryItem>
)