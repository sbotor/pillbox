package com.sbcf.pillbox.features.medications.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sbcf.pillbox.features.medications.models.Dosage
import com.sbcf.pillbox.features.medications.models.DosageUnit
import com.sbcf.pillbox.features.medications.models.TimeInterval

@Entity
data class Medication(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val description: String,

    //Dosage
    var amount : Int,
    var unit : DosageUnit,
    var interval: Int,
    var intervalType: TimeInterval
)
{
    fun getDosage() : Dosage = Dosage(this.amount, this.unit, this.interval, this.intervalType)
}