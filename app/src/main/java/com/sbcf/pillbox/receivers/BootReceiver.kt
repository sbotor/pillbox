package com.sbcf.pillbox.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sbcf.pillbox.extensions.launchGlobalAsync
import com.sbcf.pillbox.features.medicationreminders.data.repositories.MedicationRemindersRepository
import com.sbcf.pillbox.features.medicationreminders.services.MedicationAlarmScheduler
import com.sbcf.pillbox.utils.Clock
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {
    @Inject
    lateinit var repo: MedicationRemindersRepository

    @Inject
    lateinit var scheduler: MedicationAlarmScheduler

    @Inject
    lateinit var clock: Clock

    override fun onReceive(context: Context, intent: Intent) {
        // TODO: Enable this at runtime when the first notification is scheduled

        if (intent.action != Intent.ACTION_BOOT_COMPLETED) {
            return
        }

        launchGlobalAsync {
            val notifications = repo.invalidateAllAndGetEnabled()
            if (notifications.isEmpty()) {
                return@launchGlobalAsync
            }

            scheduler.scheduleAll(notifications)
            repo.updateMany(notifications)
        }
    }
}
