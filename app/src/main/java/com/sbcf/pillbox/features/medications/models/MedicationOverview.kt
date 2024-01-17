package com.sbcf.pillbox.features.medications.models

data class MedicationOverview(
    val id: Int,
    val name: String,
    val amount: Int,
    val unit: DosageUnit,
    val interval: Int,
    val intervalType: DosageTimeInterval
) {
    // TODO: Localize this
    fun getDosageString(): String {
        return "$amount ${unit.abbreviation} co $interval ${intervalType.getName(interval)}"
    }
}

