package com.sbcf.pillbox.features.medications.data.repositories

import com.sbcf.pillbox.features.medications.data.MedicationNotification
import com.sbcf.pillbox.utils.Clock
import java.util.Calendar
import javax.inject.Inject

// TODO: This is currently mocked to return a single transient object
class MedicationNotificationsRepositoryImpl @Inject constructor(private val clock: Clock) :
    MedicationNotificationsRepository {
    override suspend fun getDue(scheduledBefore: Long): List<MedicationNotification> {
        // TODO
        return listOf(create())
    }

    override suspend fun updateMany(notifications: List<MedicationNotification>) {
        // TODO
    }

    override suspend fun get(id: Int): MedicationNotification? {
        // TODO
        return create()
    }

    override suspend fun update(notification: MedicationNotification) {
        // TODO
    }

    override suspend fun getAll(): List<MedicationNotification> {
        // TODO
        return listOf(create())
    }

    private fun create(): MedicationNotification {
        val id = 1
        val calendar = clock.now()
        calendar.add(Calendar.MINUTE, 1)

        val lastScheduleCalendar = clock.now()
        lastScheduleCalendar.add(Calendar.DAY_OF_MONTH, -1)

        return MedicationNotification(
            id = id,
            title = "Notification $id",
            hour = calendar.get(Calendar.HOUR_OF_DAY),
            minute = calendar.get(Calendar.MINUTE),
            lastScheduleTimestamp = lastScheduleCalendar.timeInMillis,
            lastDeliveredTimestamp = lastScheduleCalendar.timeInMillis
        )
    }
}