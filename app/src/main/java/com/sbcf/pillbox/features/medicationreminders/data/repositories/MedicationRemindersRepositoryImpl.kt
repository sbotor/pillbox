package com.sbcf.pillbox.features.medicationreminders.data.repositories

import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder
import com.sbcf.pillbox.features.medicationreminders.models.MedicationReminderOverview
import javax.inject.Inject

class MedicationRemindersRepositoryImpl @Inject constructor(
    private val dao: MedicationReminderDao
) :
    MedicationRemindersRepository {
    override suspend fun invalidateAllAndGetEnabled(): List<MedicationReminder> {
        dao.invalidateAll()
        return dao.getAllEnabled()
    }

    override suspend fun updateMany(reminders: List<MedicationReminder>) = dao.updateMany(reminders)

    override suspend fun get(id: Long): MedicationReminder? = dao.getById(id)

    override suspend fun update(reminder: MedicationReminder) = dao.update(reminder)

    override suspend fun getAll(): List<MedicationReminderOverview> = dao.getAll()

    override suspend fun changeDeliveryTimestamp(id: Long, value: Long?) =
        dao.changeDeliveryTimestamp(id, value)

    override suspend fun add(reminder: MedicationReminder) = dao.add(reminder)
}