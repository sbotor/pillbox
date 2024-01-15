package com.sbcf.pillbox.features.medicationreminders.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.sbcf.pillbox.R
import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder
import com.sbcf.pillbox.features.medicationreminders.models.TimestampInfo
import com.sbcf.pillbox.utils.Clock
import javax.inject.Inject

class MedicationReminderPublisherImpl @Inject constructor(
    private val context: Context,
    private val clock: Clock,
    private val scheduler: MedicationAlarmScheduler,
    private val calculator: ReminderTimestampCalculator
) :
    MedicationReminderPublisher {
    override fun publish(reminder: MedicationReminder) {
        val notificationManager = getManager()
        if (!notificationManager.areNotificationsEnabled()) {
            return
        }

        val androidNotification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.medication_24)
            .setContentTitle(context.getString(R.string.medication_notification_title))
            .setContentText(reminder.title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(reminder.id.toInt(), androidNotification)

        reminder.lastDeliveryTimestamp = clock.now().timeInMillis

        val tsInfo = TimestampInfo(reminder)
        val nextTimestamp = calculator.getEarliestRepeatingTimestamp(tsInfo)

        if (nextTimestamp == null) {
            reminder.disable()
            return
        }

        reminder.enable(nextTimestamp)

        try {
            scheduler.schedule(reminder)
        } catch (e: Exception) {
            // TODO: We should probably do something
            reminder.disable()
        }
    }

    override fun ensureChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.medication_notification_channel_name)
            val channel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH)

            val notificationManager = getManager()
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "com.sbcf.pillbox.MEDICATION_NOTIFICATIONS"
    }

    private fun getManager(): NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}