package com.sbcf.pillbox.features.medications.models

data class MedicationOverview(val id: Int,
                              val name: String,
                              val amount : Int,
                              val unit : DosageUnit,
                              val interval: Int,
                              val intervalType: DosageTimeInterval
)
{
    fun getDosage() : Dosage = Dosage(amount, unit, interval, intervalType)
}

