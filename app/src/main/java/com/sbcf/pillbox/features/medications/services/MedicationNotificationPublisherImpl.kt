package com.sbcf.pillbox.features.medications.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.sbcf.pillbox.R
import com.sbcf.pillbox.features.medications.data.MedicationNotification
import com.sbcf.pillbox.utils.Clock
import javax.inject.Inject

class MedicationNotificationPublisherImpl @Inject constructor(
    private val context: Context,
    private val clock: Clock
) :
    MedicationNotificationPublisher {
    override fun publish(notification: MedicationNotification) {
        val notificationManager = getManager()
        if (!notificationManager.areNotificationsEnabled()) {
            return
        }

        val androidNotification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_medication_24)
            .setContentTitle(context.getString(R.string.medication_notification_title))
            .setContentText(notification.title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(notification.id, androidNotification)

        notification.lastDeliveredTimestamp = clock.now().timeInMillis
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
        private const val CHANNEL_ID = "PILLBOX_MEDICATION_NOTIFICATIONS"
    }

    private fun getManager(): NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}