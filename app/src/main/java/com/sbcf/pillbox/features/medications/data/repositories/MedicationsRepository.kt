package com.sbcf.pillbox.features.medications.data.repositories

import com.sbcf.pillbox.features.medications.data.Medication
import com.sbcf.pillbox.features.medications.models.MedicationOverview

interface MedicationsRepository {
    suspend fun getAllMedications(): List<MedicationOverview>
    suspend fun getAllMedicationsExcept(ids: List<Int>): List<MedicationOverview>
    suspend fun createMedication(medication: Medication)
    suspend fun getMedication(id: Int): Medication?
    suspend fun updateMedication(medication: Medication)
    suspend fun removeMedicationById(medicationId: Int)
}
