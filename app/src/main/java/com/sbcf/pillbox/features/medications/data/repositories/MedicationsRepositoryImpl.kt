package com.sbcf.pillbox.features.medications.data.repositories

import com.sbcf.pillbox.features.medications.data.Medication
import com.sbcf.pillbox.features.medications.models.MedicationOverview
import javax.inject.Inject

class MedicationsRepositoryImpl @Inject constructor(private val dao: MedicationDao) :
    MedicationsRepository {
    override var cachedMedicationOverviews: List<MedicationOverview>? = null
        private set

    override suspend fun getAllMedications(): List<MedicationOverview> {
        val result = dao.getAll()
        cachedMedicationOverviews = result
        return result
    }

    override suspend fun createMedication(medication: Medication) {
        dao.insert(medication)
    }

    override suspend fun getMedication(id: Int): Medication? = dao.get(id)

    override suspend fun updateMedication(medication: Medication) {
        dao.update(medication)
    }

    override suspend fun removeMedicationById(medicationId: Int) {
        dao.delete(medicationId)
    }

    override fun invalidateCache() {
        cachedMedicationOverviews = null
    }
}