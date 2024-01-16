package com.sbcf.pillbox.features.medications.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbcf.pillbox.features.medications.data.Medication
import com.sbcf.pillbox.features.medications.data.repositories.MedicationsRepository
import com.sbcf.pillbox.features.medications.models.Dosage
import com.sbcf.pillbox.utils.Length
import com.sbcf.pillbox.utils.validation.InputState
import com.sbcf.pillbox.utils.validation.InputValidationState
import com.sbcf.pillbox.utils.validation.InputValidators
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationFormViewModel @Inject constructor(
    private val repo: MedicationsRepository,
    validators: InputValidators
) : ViewModel() {
    class State(validators: InputValidators) {
        var name = InputState(
            InputValidationState(
                validators::required,
                validators.minLength(Length.Medication.MinNameLength)
            )
        )
        var description = InputState()
        var dosage = Dosage.default()
    }

    private var medicationId = 0

    var state = State(validators)
        private set
    var isCreating by mutableStateOf(true)
        private set
    var isEditable by mutableStateOf(false)
        private set

    fun saveMedication(callback: () -> Unit) {
        val medication = Medication(
            medicationId,
            name = state.name.value,
            description = state.description.value,
            unit = state.dosage.unit,
            amount = state.dosage.amount,
            interval = state.dosage.interval,
            intervalType = state.dosage.intervalType
        )

        viewModelScope.launch {
            if (medicationId > 0) {
                repo.updateMedication(medication)
            } else {
                repo.createMedication(medication)
            }
            callback()
        }
    }

    fun fetchMedication(id: Int, enableEditing: Boolean = false) {
        isCreating = false
        isEditable = false
        viewModelScope.launch {
            val med = repo.getMedication(id)!!
            resetForm(med)
            medicationId = med.id

            if (enableEditing) {
                isEditable = true
            }
        }
    }

    fun resetForm() {
        isCreating = true
        resetForm(null)
        isEditable = true
        medicationId = 0
    }

    fun toggleEditing() {
        isEditable = !isEditable
    }

    private fun resetForm(med: Medication?) {
        if (med == null) {
            state.name.reset("")
            state.dosage.reset()
            state.description.reset("")
        } else {
            state.name.reset(med.name)
            state.dosage.reset(med.getDosage())
            state.description.reset(med.description)
        }
    }
}