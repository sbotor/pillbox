package com.sbcf.pillbox.features.medications.data

import com.sbcf.pillbox.features.medications.models.MedicationOverview

interface MedicationsRepository {
    suspend fun getAllMedications(): List<MedicationOverview>
    suspend fun createMedication(medication: Medication)
}