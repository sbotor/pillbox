package com.sbcf.pillbox.features.medicationreminders.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sbcf.pillbox.features.medications.models.MedicationOverview


class MedicationPickerDialogState {
    var isShown by mutableStateOf(false)
        private set

    var medications by mutableStateOf<List<MedicationOverview>>(emptyList())

    fun show() {
        isShown = true
    }

    fun hide() {
        medications = emptyList()
        isShown = false
    }
}