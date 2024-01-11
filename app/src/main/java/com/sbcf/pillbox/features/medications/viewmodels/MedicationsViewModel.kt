package com.sbcf.pillbox.features.medications.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbcf.pillbox.features.medications.data.Medication
import com.sbcf.pillbox.features.medications.data.MedicationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationsViewModel @Inject constructor(private val repo: MedicationsRepository) : ViewModel() {
    private val _medications = MutableStateFlow(listOf<Medication>())
    val medications = _medications.asStateFlow();

    fun fetchMedications() {
        viewModelScope.launch {
            _medications.emit(repo.getAllMedications());
        }
    }
}