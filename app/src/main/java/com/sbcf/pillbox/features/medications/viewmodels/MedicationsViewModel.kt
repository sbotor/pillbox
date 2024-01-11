package com.sbcf.pillbox.features.medications.viewmodels

import androidx.lifecycle.ViewModel
import com.sbcf.pillbox.features.medications.data.MedicationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MedicationsViewModel @Inject constructor(private val repo: MedicationsRepository) : ViewModel() {
}