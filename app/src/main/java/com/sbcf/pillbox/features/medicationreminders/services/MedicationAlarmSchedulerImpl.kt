package com.sbcf.pillbox.features.medicationreminders.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.core.app.AlarmManagerCompat
import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder
import com.sbcf.pillbox.features.medicationreminders.receivers.MedicationAlarmReceiver
import com.sbcf.pillbox.utils.Clock
import javax.inject.Inject

class MedicationAlarmSchedulerImpl @Inject constructor(
    private val context: Context,
    private val clock: Clock
) : MedicationAlarmScheduler {
    override fun scheduleAll(reminders: List<MedicationReminder>) {
        val alarmManager = getAlarmManager()

        for (notification in reminders) {
            schedule(alarmManager, notification)
        }
    }

    override fun schedule(reminder: MedicationReminder) {
        schedule(getAlarmManager(), reminder)
    }

    override fun unschedule(reminder: MedicationReminder) {
        val intent = getIntent(reminder.id) ?: return

        val alarmManager = getAlarmManager()
        alarmManager.cancel(intent)
    }

    private fun schedule(alarmManager: AlarmManager, reminder: MedicationReminder) {
        var deliveryTimestamp = reminder.nextDeliveryTimestamp ?: return

        val intent = getOrCreateIntent(reminder.id)

        val now = clock.now().timeInMillis
        if (now > deliveryTimestamp) {
            deliveryTimestamp = now + 1000
        }

        AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager,
            AlarmManager.RTC,
            deliveryTimestamp,
            intent
        )

        reminder.scheduledTimestamp = deliveryTimestamp
    }

    private fun getAlarmManager(): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private fun getOrCreateIntent(reminderId: Int): PendingIntent =
        getIntentCore(
            reminderId,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )!!

    private fun getIntent(reminderId: Int): PendingIntent? =
        getIntentCore(
            reminderId,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_NO_CREATE
        )

    private fun getIntentCore(reminderId: Int, flags: Int): PendingIntent? {
        val intent = Intent().also {
            it.component =
                ComponentName(context, MedicationAlarmReceiver::class.java)
            it.action = MedicationAlarmReceiver.ACTION
            it.data = MedicationAlarmReceiver.createData(reminderId)
        }

        return PendingIntent.getBroadcast(
            context,
            reminderId,
            intent,
            flags
        )
    }
}