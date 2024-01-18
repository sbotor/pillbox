package com.sbcf.pillbox.features.medicationreminders.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbcf.pillbox.features.medicationreminders.data.DayOfWeek
import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder
import com.sbcf.pillbox.features.medicationreminders.data.ReminderWithMedications
import com.sbcf.pillbox.features.medicationreminders.data.repositories.MedicationRemindersRepository
import com.sbcf.pillbox.features.medicationreminders.models.MedicationPickerDialogState
import com.sbcf.pillbox.features.medicationreminders.models.ReminderMedicationItem
import com.sbcf.pillbox.features.medicationreminders.models.ReminderTimeState
import com.sbcf.pillbox.features.medicationreminders.models.TimestampInfo
import com.sbcf.pillbox.features.medicationreminders.services.MedicationAlarmScheduler
import com.sbcf.pillbox.features.medicationreminders.services.ReminderTimestampCalculator
import com.sbcf.pillbox.features.medications.data.repositories.MedicationsRepository
import com.sbcf.pillbox.features.medications.models.MedicationOverview
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
    private val formatter: DisplayFormatter,
    private val medRepo: MedicationsRepository
) :
    ViewModel() {
    class State(formatter: DisplayFormatter) {
        val days = mutableStateListOf(false, false, false, false, false, false, false)
        val time = ReminderTimeState(formatter)
        val title = InputState()
        val medications = mutableStateListOf<ReminderMedicationItem>()
        val medPicker = MedicationPickerDialogState()

        fun toggleDay(idx: Int): Boolean {
            if (idx >= 7) {
                return false
            }

            days[idx] = !days[idx]
            return days[idx]
        }

        fun reset(reminder: ReminderWithMedications?, now: Calendar) {
            if (reminder == null) {
                time.hour = now.get(Calendar.HOUR_OF_DAY)
                time.minute = now.get(Calendar.MINUTE)
                title.reset("")

                days.replaceAll { false }

                medications.clear()
            } else {
                val rem = reminder.reminder
                time.hour = rem.hour
                time.minute = rem.minute
                title.reset(rem.title)

                var i = 0
                days.replaceAll { rem.days.isSet(i++) }

                medications.clear()
                medications.addAll(reminder.medications)
            }
        }
    }

    private var remWithMeds = ReminderWithMedications(MedicationReminder(), emptyList())
    private var isCreated = false

    val state = State(formatter)

    suspend fun fetchReminder(id: Int?) {
        if (id == null) {
            state.reset(null, clock.now())
            return
        }

        val rem = repo.getReminderWithMedications(id)!!
        remWithMeds = rem
        state.reset(rem, clock.now())
        isCreated = true
    }

    fun addMed(med: MedicationOverview) {
        val mapped = ReminderMedicationItem(med.id, med.name, med.dosageAmount, med.dosageUnit)
        val existingIdx = state.medications.indexOfFirst { it.id == med.id }
        if (existingIdx >= 0) {
            state.medications[existingIdx] = mapped
        } else {
            state.medications.add(mapped)
        }
    }

    fun removeMed(medicationId: Int) {
        state.medications.removeIf { it.id == medicationId }
    }

    fun save() {
        val rem = remWithMeds.reminder
        val meds = state.medications.toList()

        rem.hour = state.time.hour
        rem.minute = state.time.minute
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
            val newRem = ReminderWithMedications(rem, meds)

            if (isCreated) {
                repo.updateReminderWithMedications(newRem)
            } else {
                rem.id = repo.addReminderWithMedications(newRem)
            }

            isCreated = true
            remWithMeds = newRem
            scheduler.schedule(rem)
        }
    }

    fun formatDayOfWeek(day: DayOfWeek) = formatter.dayOfWeek(day)

    suspend fun fetchMedications() {
        val ids = state.medications.map { it.id }
        state.medPicker.medications = medRepo.getAllMedicationsExcept(ids)
    }
}