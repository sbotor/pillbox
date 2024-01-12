package com.sbcf.pillbox.features.medications.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbcf.pillbox.features.medications.data.Medication
import com.sbcf.pillbox.features.medications.data.MedicationsRepository
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
                validators.length(Length.Medication.MinNameLength, Length.Medication.MaxNameLength)
            )
        )
        var description =
            InputState(InputValidationState(validators.maxLength(Length.Medication.MaxDescriptionLength)))
        var dosage =
            InputState(InputValidationState(validators.maxLength(Length.Medication.MaxDosageLength)))
    }

    var state = State(validators)
        private set

    fun createMedication() {
        val medication = Medication(
            0,
            name = state.name.value,
            description = state.description.value,
            dosage = state.dosage.value
        )
        viewModelScope.launch {
            repo.createMedication(medication)
        }
    }
}