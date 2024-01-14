package com.sbcf.pillbox.features.medications.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.core.app.AlarmManagerCompat
import com.sbcf.pillbox.features.medications.data.MedicationNotification
import com.sbcf.pillbox.features.medications.receivers.MedicationAlarmReceiver
import com.sbcf.pillbox.utils.Clock
import javax.inject.Inject

class MedicationAlarmSchedulerImpl @Inject constructor(
    private val context: Context,
    private val clock: Clock
) : MedicationAlarmScheduler {
    override suspend fun scheduleAll(notifications: List<MedicationNotification>) {
        val alarmManager = getAlarmManager()

        for (notification in notifications) {
            schedule(alarmManager, notification)
        }
    }

    private fun schedule(alarmManager: AlarmManager, notification: MedicationNotification) {
        val intent = Intent().also {
            it.component =
                ComponentName(context, MedicationAlarmReceiver::class.java)
            it.action = MedicationAlarmReceiver.ACTION
            it.data = MedicationAlarmReceiver.createData(notification.id)
        }

        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val now = clock.now().timeInMillis
        var plannedTime = clock.today(notification.hour, notification.minute).timeInMillis
        if (now > plannedTime) {
            plannedTime = now
        }

        AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager,
            AlarmManager.RTC,
            plannedTime,
            pendingIntent
        )

        notification.lastScheduleTimestamp = plannedTime
    }

    private fun getAlarmManager(): AlarmManager {
        // TODO: This probably should never return null
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }
}