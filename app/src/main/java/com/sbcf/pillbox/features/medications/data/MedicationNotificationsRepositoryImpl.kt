package com.sbcf.pillbox.features.medications.data

import com.sbcf.pillbox.services.Clock
import java.util.Calendar
import javax.inject.Inject

class MedicationNotificationsRepositoryImpl @Inject constructor(private val clock: Clock) :
    MedicationNotificationsRepository {
    override suspend fun getDue(scheduledBefore: Long): List<MedicationNotification> {
        val calendar = clock.now()
        calendar.roll(Calendar.MINUTE, 1)

        val lastScheduleCalendar = clock.now()
        lastScheduleCalendar.roll(Calendar.DAY_OF_MONTH, -1)

        return List(1) {
            MedicationNotification(
                id = it,
                message = "Notification $it",
                hour = calendar.get(Calendar.HOUR_OF_DAY),
                minute = calendar.get(Calendar.MINUTE),
                lastScheduleTimestamp = lastScheduleCalendar.timeInMillis,
                lastDeliveredTimestamp = lastScheduleCalendar.timeInMillis
            )
        }
    }

    override suspend fun updateMany(notifications: List<MedicationNotification>) {
        // TODO
    }

    override suspend fun get(id: Int): MedicationNotification? {
        // TODO
        return null
    }

    override suspend fun update(notification: MedicationNotification) {
        // TODO
    }
}