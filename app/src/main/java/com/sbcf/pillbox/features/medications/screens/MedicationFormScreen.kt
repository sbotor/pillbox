package com.sbcf.pillbox.features.medications.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sbcf.pillbox.R
import com.sbcf.pillbox.components.TextInput
import com.sbcf.pillbox.features.medications.viewmodels.MedicationFormViewModel

@Composable
fun MedicationFormScreen(vm: MedicationFormViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        TextInput(
            state = vm.state.name,
            label = { Text(text = stringResource(id = R.string.medication_name)) }
        )
        TextInput(
            state = vm.state.description,
            label = { Text(text = stringResource(id = R.string.medication_description)) }
        )
        TextInput(
            state = vm.state.dosage,
            label = { Text(text = stringResource(id = R.string.medication_dosage)) }
        )
        Button(onClick = { vm.createMedication() }) {
            Text(text = stringResource(id = R.string.add_medication))
        }
    }
}