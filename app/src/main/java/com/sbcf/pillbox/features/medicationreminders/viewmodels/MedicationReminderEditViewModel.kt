package com.sbcf.pillbox.features.medicationreminders.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder
import com.sbcf.pillbox.features.medicationreminders.data.repositories.MedicationRemindersRepository
import com.sbcf.pillbox.features.medicationreminders.services.MedicationAlarmScheduler
import com.sbcf.pillbox.utils.Clock
import com.sbcf.pillbox.utils.validation.InputState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MedicationReminderEditViewModel @Inject constructor(
    private val repo: MedicationRemindersRepository,
    private val scheduler: MedicationAlarmScheduler,
    private val clock: Clock
) :
    ViewModel() {
    class State(now: Calendar) {
        val days = mutableStateListOf(false, false, false, false, false, false, false)
        var hour by mutableIntStateOf(now.get(Calendar.HOUR_OF_DAY))
            private set
        var minute by mutableIntStateOf(now.get(Calendar.MINUTE))
            private set
        val title = InputState()

        fun toggleDay(idx: Int): Boolean {
            if (idx >= 7) {
                return false
            }

            days[idx] = !days[idx]
            return days[idx]
        }

        fun setTime(hour: Int, minute: Int) {
            this.hour = hour
            this.minute = minute
        }
    }

    private var reminder = MedicationReminder().also {
        it.hour = clock.now().get(Calendar.HOUR_OF_DAY)
        it.minute = clock.now().get(Calendar.MINUTE)
    }
    private var isCreated = false
    private var shouldRecalculateTimestamp = true

    val state = State(clock.now())
    var showTimePicker by mutableStateOf(false)

    fun save() {
        val rem = reminder
        rem.hour = state.hour
        rem.minute = state.minute
        rem.title = state.title.value

        for ((i, day) in state.days.withIndex()) {
            rem.days.set(i, day)
        }

        if (!rem.isEnabled) {
            rem.enable(Long.MIN_VALUE)
            shouldRecalculateTimestamp = true
        }

        if (shouldRecalculateTimestamp) {
            val timestamp = rem.getEarliestTimestamp(clock.now())
            if (timestamp != null) {
                rem.enable(timestamp)
            }
        }

        viewModelScope.launch {
            if (isCreated) {
                repo.update(rem)
            } else {
                rem.id = repo.add(rem)
            }
            isCreated = true
            scheduler.schedule(rem)
        }
    }
}