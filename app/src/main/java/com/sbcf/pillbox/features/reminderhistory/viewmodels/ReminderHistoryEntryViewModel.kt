package com.sbcf.pillbox.features.reminderhistory.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbcf.pillbox.features.reminderhistory.data.repositories.ReminderHistoryRepository
import com.sbcf.pillbox.utils.Clock
import com.sbcf.pillbox.utils.DisplayFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderHistoryEntryViewModel @Inject constructor(
    private val repo: ReminderHistoryRepository,
    private val clock: Clock,
    private val formatter: DisplayFormatter
) :
    ViewModel() {
    private var entryId = 0

    var isConfirmed by mutableStateOf(false)
        private set
    var description by mutableStateOf("")
        private set
    var formattedDeliveryDateTime by mutableStateOf("")
        private set
    var items by mutableStateOf(emptyList<String>())
        private set

    suspend fun fetchEntryAndMarkAsViewed(id: Int) {
        val entry = repo.get(id)!!
        entryId = id
        if (!entry.data.wasViewed) {
            repo.markAsViewed(id)
        }

        isConfirmed = entry.data.isConfirmed
        description = entry.data.description

        formattedDeliveryDateTime =
            formatter.dayOfTheWeekAndTime(clock.fromTimestamp(entry.data.deliveredTimestamp))

        items = entry.items.sortedBy { it.ordinal }.map { it.description }.toList()
    }

    fun toggleConfirmation() {
        viewModelScope.launch {
            val newValue = !isConfirmed
            repo.markConfirmation(entryId, newValue)
            isConfirmed = newValue
        }
    }
}