package com.sbcf.pillbox.features.medicationreminders.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbcf.pillbox.features.medicationreminders.data.DayOfWeek
import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder
import com.sbcf.pillbox.features.medicationreminders.data.repositories.MedicationRemindersRepository
import com.sbcf.pillbox.features.medicationreminders.models.TimestampInfo
import com.sbcf.pillbox.features.medicationreminders.services.MedicationAlarmScheduler
import com.sbcf.pillbox.features.medicationreminders.services.ReminderTimestampCalculator
import com.sbcf.pillbox.utils.Clock
import com.sbcf.pillbox.utils.DisplayFormatter
import com.sbcf.pillbox.utils.validation.InputState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MedicationReminderEditViewModel @Inject constructor(
    private val repo: MedicationRemindersRepository,
    private val scheduler: MedicationAlarmScheduler,
    private val clock: Clock,
    private val calculator: ReminderTimestampCalculator,
    private val formatter: DisplayFormatter
) :
    ViewModel() {
    class State {
        val days = mutableStateListOf(false, false, false, false, false, false, false)
        var hour by mutableIntStateOf(0)
            private set
        var minute by mutableIntStateOf(0)
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

        fun reset(reminder: MedicationReminder?, now: Calendar) {
            if (reminder == null) {
                days.replaceAll { false }
                hour = now.get(Calendar.HOUR_OF_DAY)
                minute = now.get(Calendar.MINUTE)
                title.reset("")
            } else {
                var i = 0
                days.replaceAll { reminder.days.isSet(i++) }
                hour = reminder.hour
                minute = reminder.minute
                title.reset(reminder.title)
            }
        }
    }

    private var reminder = MedicationReminder()
    private var isCreated = false

    val state = State()
    var showTimePicker by mutableStateOf(false)

    suspend fun fetchReminder(id: Long?) {
        if (id == null) {
            state.reset(null, clock.now())
            return
        }

        val rem = repo.get(id)!!
        reminder = rem
        state.reset(rem, clock.now())
        isCreated = true
    }

    fun save() {
        val rem = reminder
        rem.hour = state.hour
        rem.minute = state.minute
        rem.title = state.title.value

        for ((i, day) in state.days.withIndex()) {
            rem.days.set(i, day)
        }

        rem.enable(Long.MIN_VALUE)
        val tsInfo = TimestampInfo(rem)
        val timestamp = calculator.getEarliestTimestamp(tsInfo)
        if (timestamp != null) {
            rem.enable(timestamp)
        } else {
            rem.disable()
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

    fun formatTime() = formatter.time(state.hour, state.minute)

    fun formatDayOfWeek(day: DayOfWeek) = formatter.dayOfWeek(day)
}