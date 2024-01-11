package com.sbcf.pillbox.features.medications.data

interface MedicationsRepository {
    suspend fun getAllMedications(): List<Medication>
}