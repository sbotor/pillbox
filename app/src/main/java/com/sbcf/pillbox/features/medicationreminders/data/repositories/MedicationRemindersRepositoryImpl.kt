package com.sbcf.pillbox.features.medicationreminders.data.repositories

import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder
import com.sbcf.pillbox.features.medicationreminders.data.ReminderWithMedications
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

    override suspend fun get(id: Int): MedicationReminder? = dao.getById(id)

    override suspend fun update(reminder: MedicationReminder) = dao.update(reminder)

    override suspend fun getAll(): List<MedicationReminderOverview> = dao.getAll()
    override suspend fun getNext(): ReminderWithMedications? = dao.getNextEnabled()
    override suspend fun changeDeliveryTimestamp(id: Int, value: Long?) =
        dao.changeDeliveryTimestamp(id, value)

    override suspend fun add(reminder: MedicationReminder): Int {
        val id = dao.add(reminder)
        return id.toInt()
    }

    override suspend fun deleteById(id: Int) = dao.delete(id)
    override suspend fun getReminderWithMedications(id: Int): ReminderWithMedications? =
        dao.getReminderWithMedications(id)

    override suspend fun updateReminderWithMedications(rem: ReminderWithMedications) =
        dao.updateReminderWithMedications(rem)

    override suspend fun addReminderWithMedications(rem: ReminderWithMedications): Int =
        dao.addReminderWithMedications(rem)
}