package com.sbcf.pillbox.features.reminderhistory.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbcf.pillbox.features.reminderhistory.data.ReminderHistoryEntry
import com.sbcf.pillbox.features.reminderhistory.data.repositories.ReminderHistoryRepository
import com.sbcf.pillbox.utils.Clock
import com.sbcf.pillbox.utils.DisplayFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderHistoryViewModel @Inject constructor(
    private val repo: ReminderHistoryRepository,
    private val clock: Clock,
    private val formatter: DisplayFormatter
) : ViewModel() {

    var reminders by mutableStateOf(listOf<ReminderHistoryEntry>())
        private set

    suspend fun fetchReminders() {
        reminders = repo.getNewest(20)
    }

    fun markReminderAsViewed(id: Int) {
        viewModelScope.launch {
            repo.markAsViewed(id)
        }
    }

    fun formattedDelivery(timestamp: Long): String {
        val cal = clock.fromTimestamp(timestamp)
        return formatter.dayOfWeekAndDateTime(cal)
    }
}