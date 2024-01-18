package com.sbcf.pillbox.features.medications.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sbcf.pillbox.features.medications.models.Dosage
import com.sbcf.pillbox.features.medications.models.DosageState
import com.sbcf.pillbox.features.medications.models.DosageTimeInterval
import com.sbcf.pillbox.features.medications.models.DosageUnit

@Entity
data class Medication(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,

    //Dosage
    override var amount: Int,
    override var unit: DosageUnit,
    override var interval: Int,
    override var intervalType: DosageTimeInterval
) : Dosage