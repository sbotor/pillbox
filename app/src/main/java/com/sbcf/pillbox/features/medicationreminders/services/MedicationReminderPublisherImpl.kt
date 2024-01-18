package com.sbcf.pillbox.features.medicationreminders.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.sbcf.pillbox.MainActivity
import com.sbcf.pillbox.R
import com.sbcf.pillbox.features.medicationreminders.data.ReminderWithMedications
import com.sbcf.pillbox.features.medicationreminders.models.TimestampInfo
import com.sbcf.pillbox.features.reminderhistory.ReminderHistoryScreens
import com.sbcf.pillbox.features.reminderhistory.data.ReminderHistoryEntry
import com.sbcf.pillbox.features.reminderhistory.data.ReminderHistoryEntryData
import com.sbcf.pillbox.features.reminderhistory.data.ReminderHistoryItem
import com.sbcf.pillbox.features.reminderhistory.data.repositories.ReminderHistoryRepository
import com.sbcf.pillbox.utils.Clock
import javax.inject.Inject

class MedicationReminderPublisherImpl @Inject constructor(
    private val context: Context,
    private val clock: Clock,
    private val scheduler: MedicationAlarmScheduler,
    private val calculator: ReminderTimestampCalculator,
    private val historyRepo: ReminderHistoryRepository
) :
    MedicationReminderPublisher {
    override suspend fun publishAndCalculateNextTimestamp(reminder: ReminderWithMedications) {
        val notificationManager = getManager()
        if (!notificationManager.areNotificationsEnabled()) {
            return
        }

        val rem = reminder.reminder

        val nowTimestamp = clock.now().timeInMillis

        val meds = reminder.medications.map { ReminderHistoryItem(0, 0, it.name) }
        val historyEntry = ReminderHistoryEntry(
            ReminderHistoryEntryData(0, nowTimestamp, rem.title),
            meds
        )

        val entryId = historyRepo.create(historyEntry)
        val intent = getIntent(entryId)

        val androidNotification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.medication_24)
            .setContentTitle(context.getString(R.string.medication_notification_title))
            .setContentText(rem.title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(intent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(rem.id, androidNotification)

        rem.lastDeliveryTimestamp = nowTimestamp

        val tsInfo = TimestampInfo(rem)
        val nextTimestamp = calculator.getEarliestRepeatingTimestamp(tsInfo)

        if (nextTimestamp == null) {
            rem.disable()
            return
        }

        rem.enable(nextTimestamp)

        try {
            scheduler.schedule(rem)
        } catch (e: Exception) {
            // TODO: We should probably do something
            rem.disable()
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

    private fun getIntent(entryId: Int): PendingIntent {
        val intent = Intent().also {
            it.component = ComponentName(context, MainActivity::class.java)
            it.action = ReminderHistoryScreens.ReminderHistoryEntry.DEEP_LINK_ACTION
            it.data = ReminderHistoryScreens.ReminderHistoryEntry.createDeepLinkPattern(entryId)
        }

        return PendingIntent.getActivity(context, entryId, intent, PendingIntent.FLAG_IMMUTABLE)
    }
}