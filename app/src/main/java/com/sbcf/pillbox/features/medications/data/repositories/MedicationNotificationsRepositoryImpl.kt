package com.sbcf.pillbox.features.medications.data.repositories

import com.sbcf.pillbox.features.medications.data.MedicationNotification
import com.sbcf.pillbox.services.Clock
import java.util.Calendar
import javax.inject.Inject

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

    private fun create(): MedicationNotification {
        val id = 1
        val calendar = clock.now()
        calendar.roll(Calendar.MINUTE, 1)

        val lastScheduleCalendar = clock.now()
        lastScheduleCalendar.roll(Calendar.DAY_OF_MONTH, -1)

        return MedicationNotification(
            id = id,
            message = "Notification $id",
            hour = calendar.get(Calendar.HOUR_OF_DAY),
            minute = calendar.get(Calendar.MINUTE),
            lastScheduleTimestamp = lastScheduleCalendar.timeInMillis,
            lastDeliveredTimestamp = lastScheduleCalendar.timeInMillis
        )
    }
}