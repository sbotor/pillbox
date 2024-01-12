package com.sbcf.pillbox.features.medications.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbcf.pillbox.features.medications.data.Medication
import com.sbcf.pillbox.features.medications.data.MedicationsRepository
import com.sbcf.pillbox.features.medications.models.MedicationOverview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationListViewModel @Inject constructor(private val repo: MedicationsRepository) :
    ViewModel() {
    private var _medications = listOf<MedicationOverview>()

    var medications by mutableStateOf(listOf<MedicationOverview>())
        private set
    var isSearching by mutableStateOf(false)
        private set
    var textFilter by mutableStateOf("")
        private set

    fun fetchMedications() {
        viewModelScope.launch {
            _medications = repo.getAllMedications()
            medications = _medications
        }
    }

    fun searchByName(text: String = "") {
        isSearching = true
        textFilter = text

        if (textFilter.isEmpty()) {
            medications = _medications
            return
        }

        val trimmed = textFilter.trim()

        medications = _medications.filter {
            it.name.contains(textFilter, true) || it.name.contains(trimmed, true)
        }
    }

    fun stopSearching() {
        isSearching = false
        textFilter = ""
    }
}