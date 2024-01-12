package com.sbcf.pillbox.features.medications.data

import com.sbcf.pillbox.features.medications.models.MedicationOverview
import javax.inject.Inject

class MedicationsRepositoryImpl @Inject constructor(private val dao: MedicationDao) :
    MedicationsRepository {
    override suspend fun getAllMedications(): List<MedicationOverview> {
        //return List(100) { MedicationOverview(it, "Lek #$it") }
        return dao.getAll()
    }

    override suspend fun createMedication(medication: Medication) {
        dao.insert(medication)
    }
}