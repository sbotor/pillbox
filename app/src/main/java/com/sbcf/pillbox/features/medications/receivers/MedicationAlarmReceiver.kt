package com.sbcf.pillbox.features.medications.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log

class MedicationAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.i("Pillbox", MedicationAlarmReceiver::class.toString())
    }

    companion object {
        const val ACTION = "PILLBOX_NOTIFICATION_ALARM"

        fun createData(notificationId: Int): Uri {
            return Uri.parse("content://medicationNotifications/$notificationId")
        }
    }
}