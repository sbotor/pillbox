package com.sbcf.pillbox.features.medications.models

data class MedicationOverview(
    val id: Int,
    val name: String,
    val dosageAmount: Int,
    val dosageUnit: DosageUnit,
    val dosageInterval: Int,
    val dosageIntervalType: DosageTimeInterval
) {
    // TODO: Localize this
    fun getDosageString(): String {
        return "$dosageAmount ${dosageUnit.abbreviation} co $dosageInterval ${
            dosageIntervalType.getName(
                dosageInterval
            )
        }"
    }
}

