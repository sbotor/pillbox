package com.sbcf.pillbox.features.medications.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sbcf.pillbox.features.medications.models.Dosage
import com.sbcf.pillbox.features.medications.models.DosageTimeInterval
import com.sbcf.pillbox.features.medications.models.DosageUnit

@Entity
data class Medication(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val dosageAmount: Int,
    val dosageUnit: DosageUnit,
    val dosageInterval: Int,
    val dosageIntervalType: DosageTimeInterval
) : Dosage {
    override val amount: Int
        get() = dosageAmount
    override val unit: DosageUnit
        get() = dosageUnit
    override val interval: Int
        get() = dosageInterval
    override val intervalType: DosageTimeInterval
        get() = dosageIntervalType
}