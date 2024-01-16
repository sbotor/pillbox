package com.sbcf.pillbox.features.medicationreminders.viewmodels

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbcf.pillbox.features.medicationreminders.data.DayOfWeek
import com.sbcf.pillbox.features.medicationreminders.data.repositories.MedicationRemindersRepository
import com.sbcf.pillbox.features.medicationreminders.models.MedicationReminderOverview
import com.sbcf.pillbox.features.medicationreminders.models.TimestampInfo
import com.sbcf.pillbox.features.medicationreminders.services.MedicationAlarmScheduler
import com.sbcf.pillbox.features.medicationreminders.services.ReminderTimestampCalculator
import com.sbcf.pillbox.utils.Clock
import com.sbcf.pillbox.utils.DisplayFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationRemindersViewModel @Inject constructor(
    private val repo: MedicationRemindersRepository,
    private val scheduler: MedicationAlarmScheduler,
    private val clock: Clock,
    private val calculator: ReminderTimestampCalculator,
    private val formatter: DisplayFormatter
) :
    ViewModel() {
    // TODO: This flow is kind of wacky. Need to find a way to determine if the user denied the permission.
    // https://developer.android.com/develop/ui/views/notifications/notification-permission#user-select-dont-allow
    class NotificationPermissionState {
        var isGranted by mutableStateOf(false)
            private set
        var isEstablished by mutableStateOf(false)
            private set

        fun check(context: Context) {
            if (isEstablished) {
                return
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                isGranted = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                isGranted = true
                isEstablished = true
            }
        }

        fun shouldRequest(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
        }

        fun setRequestResult(value: Boolean) {
            isEstablished = true
            isGranted = value
        }
    }

    val notificationPermission = NotificationPermissionState()

    var reminders by mutableStateOf(emptyList<MedicationReminderOverview>())
        private set

    suspend fun fetchReminders() {
        reminders = repo.getAll()
    }

    fun removeReminder(id: Long) {
        viewModelScope.launch {
            repo.deleteById(id)
            fetchReminders()
        }
    }

    fun toggleReminder(reminder: MedicationReminderOverview) {
        if (reminder.nextDeliveryTimestamp != null) {
            // TODO: It would probably be good to unschedule the alarm
            viewModelScope.launch {
                repo.changeDeliveryTimestamp(reminder.id, null)
                fetchReminders()
            }
            return
        }

        val tsInfo = TimestampInfo(reminder)
        val timestamp = calculator.getEarliestTimestamp(tsInfo) ?: return

        viewModelScope.launch {
            repo.changeDeliveryTimestamp(reminder.id, timestamp)
            val rem = repo.get(reminder.id)
            if (rem != null) {
                scheduler.schedule(rem)
            }
            fetchReminders()
        }
    }

    fun formatNextNotificationTime(reminder: MedicationReminderOverview): String {
        if (reminder.nextDeliveryTimestamp == null) {
            return ""
        }

        val cal = clock.fromTimestamp(reminder.nextDeliveryTimestamp)

        return formatter.dateTime(cal)
    }

    fun formatReminderDisplayLabel(reminder: MedicationReminderOverview): String {
        val displayTime = formatter.time(reminder.hour, reminder.minute)
        if (reminder.title.isNotEmpty()) {
            return "${reminder.title} - $displayTime"
        }

        return displayTime
    }

    fun formatReminderDisplayTime(reminder: MedicationReminderOverview) =
        formatter.time(reminder.hour, reminder.minute)

    fun formatShortDayOfWeek(day: DayOfWeek) = formatter.dayOfWeekShort(day)
}