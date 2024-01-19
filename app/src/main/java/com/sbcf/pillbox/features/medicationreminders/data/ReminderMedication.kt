package com.sbcf.pillbox.features.medicationreminders.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.sbcf.pillbox.features.medicationreminders.models.ReminderMedicationItem
import com.sbcf.pillbox.features.medications.data.Medication

@Entity(primaryKeys = ["reminderId", "medicationId"])
data class ReminderMedication(
    val reminderId: Int,
    
    @ColumnInfo(index = true)
    val medicationId: Int
)

data class ReminderWithMedications(
    @Embedded
    val reminder: MedicationReminder,

    @Relation(
        entity = Medication::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            ReminderMedication::class,
            parentColumn = "reminderId",
            entityColumn = "medicationId"
        )
    )
    val medications: List<ReminderMedicationItem>
)