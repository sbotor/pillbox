package com.sbcf.pillbox.features.medications.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.sbcf.pillbox.extensions.launchGlobalAsync
import com.sbcf.pillbox.features.medications.data.MedicationNotification
import com.sbcf.pillbox.features.medications.data.repositories.MedicationNotificationsRepository
import com.sbcf.pillbox.features.medications.services.MedicationNotificationPublisher
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MedicationAlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var repo: MedicationNotificationsRepository

    @Inject
    lateinit var publisher: MedicationNotificationPublisher

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != ACTION) {
            return
        }

        launchGlobalAsync {
            val notificationId = parseNotificationId(intent) ?: return@launchGlobalAsync
            val notification = repo.get(notificationId) ?: return@launchGlobalAsync

            publisher.publish(notification)
            repo.update(notification)
        }
    }

    companion object {
        const val ACTION = "PILLBOX_NOTIFICATION_ALARM"

        fun createData(notificationId: Int): Uri {
            return Uri.parse("content://medicationNotifications/$notificationId")
        }

        private fun parseNotificationId(intent: Intent): Int? {
            val str = intent.data?.lastPathSegment
            if (str.isNullOrEmpty()) {
                return null
            }

            return str.toIntOrNull()
        }
    }
}