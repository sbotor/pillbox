package com.sbcf.pillbox.features.medications.data

import javax.inject.Inject

class MedicationsRepositoryImpl @Inject constructor(private val medicationDao: MedicationDao) :
    MedicationsRepository {
    override suspend fun getAllMedications(): List<Medication> = medicationDao.getAll()
}