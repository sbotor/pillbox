package com.sbcf.pillbox.features.home.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder
import com.sbcf.pillbox.features.medicationreminders.data.repositories.MedicationRemindersRepository
import com.sbcf.pillbox.features.reminderhistory.data.ReminderHistoryEntry
import com.sbcf.pillbox.features.reminderhistory.data.repositories.ReminderHistoryRepository
import com.sbcf.pillbox.utils.Clock
import com.sbcf.pillbox.utils.DisplayFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val reminderRepo: MedicationRemindersRepository,
    private val historyRepo: ReminderHistoryRepository,
    private val clock: Clock,
    private val formatter: DisplayFormatter
) : ViewModel() {
    var reminder: MedicationReminder? by mutableStateOf(MedicationReminder())
        private set

    var history by mutableStateOf(listOf<ReminderHistoryEntry>())
        private set

    suspend fun fetchInformation() {
        reminder = reminderRepo.getNext()
        history = historyRepo.getNewestUnconfirmed(3)
    }

    fun formatNextNotificationTime(timestamp: Long?): String {
        if (timestamp == null) {
            return ""
        }

        val cal = clock.fromTimestamp(timestamp)

        return formatter.dayOfWeekAndDateTime(cal)
    }
}