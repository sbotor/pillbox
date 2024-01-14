package com.sbcf.pillbox.features.medications.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbcf.pillbox.features.medications.data.repositories.MedicationsRepository
import com.sbcf.pillbox.features.medications.models.MedicationOverview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationListViewModel @Inject constructor(private val repo: MedicationsRepository) :
    ViewModel() {
    private var allMedications = listOf<MedicationOverview>()

    var medications by mutableStateOf(listOf<MedicationOverview>())
        private set
    var isSearching by mutableStateOf(false)
        private set
    var textFilter by mutableStateOf("")
        private set

    fun fetchMedications() {
        viewModelScope.launch {
            fetchMedicationsCore()
        }
    }

    fun searchByName(text: String = "") {
        isSearching = true
        textFilter = text

        if (textFilter.isEmpty()) {
            medications = emptyList()
            return
        }

        val trimmed = textFilter.trim()

        viewModelScope.launch {
            medications = allMedications.filter {
                it.name.contains(textFilter, true) || it.name.contains(trimmed, true)
            }
        }
    }

    fun stopSearching() {
        isSearching = false
        textFilter = ""
        medications = allMedications
    }

    fun removeMedication(medicationId: Int){
        viewModelScope.launch {
            repo.removeMedicationById(medicationId)
            fetchMedicationsCore()
        }
    }

    private suspend fun fetchMedicationsCore() {
        allMedications = repo.getAllMedications()
        medications = allMedications
    }
}