package com.sbcf.pillbox.features.medications.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.sbcf.pillbox.R
import com.sbcf.pillbox.components.TextInput
import com.sbcf.pillbox.features.medications.components.MedicationFormActions
import com.sbcf.pillbox.features.medications.viewmodels.MedicationFormViewModel
import com.sbcf.pillbox.utils.Dimens
import com.sbcf.pillbox.utils.Length

data class MedicationDetailsCallbacks(
    val onBackClick: () -> Unit,
    val onSaveClick: () -> Unit
)

@Composable
fun MedicationDetailsScreen(
    medicationId: Int,
    initiallyEditable: Boolean,
    vm: MedicationFormViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) = MedicationFormScreen(
    vm,
    MedicationDetailsCallbacks(
        onBackClick = onBackClick,
        onSaveClick = { vm.saveMedication(callback = { vm.toggleEditing() }) })
) {
    vm.fetchMedication(medicationId, initiallyEditable)
}

@Composable
fun AddMedicationScreen(
    vm: MedicationFormViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) = MedicationFormScreen(
    vm,
    MedicationDetailsCallbacks(
        onBackClick = onBackClick,
        onSaveClick = { vm.saveMedication(callback = onBackClick) })
) {
    vm.resetForm()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MedicationFormScreen(
    vm: MedicationFormViewModel = hiltViewModel(),
    callbacks: MedicationDetailsCallbacks,
    onInit: suspend () -> Unit
) {
    LaunchedEffect(key1 = vm, key2 = onInit, block = {
        onInit()
    })

    Scaffold(topBar = {
        TopAppBar(
            title = {
                val titleId =
                    if (vm.isCreating) R.string.add_medication else R.string.medication_details
                Text(text = stringResource(id = titleId))
            },
            navigationIcon = {
                IconButton(
                    onClick = callbacks.onBackClick
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }, actions = {
                MedicationFormActions(
                    isCreating = vm.isCreating,
                    isEditable = vm.isEditable,
                    onSaveClick = { callbacks.onSaveClick() },
                    onEditToggle = vm::toggleEditing
                )
            })
    }) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Dimens.PaddingNormal)
        ) {
            TextInput(
                state = vm.state.name,
                label = { Text(text = stringResource(id = R.string.medication_name)) },
                modifier = Modifier.fillMaxWidth(),
                maxLength = Length.Medication.MaxNameLength,
                enabled = vm.isEditable
            )
            TextInput(
                state = vm.state.dosage,
                label = { Text(text = stringResource(id = R.string.medication_dosage)) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = stringResource(id = R.string.medication_dosage_placeholder)) },
                maxLength = Length.Medication.MaxDosageLength,
                enabled = vm.isEditable
            )
            TextInput(
                state = vm.state.description,
                label = { Text(text = stringResource(id = R.string.medication_description)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                maxLength = Length.Medication.MaxDescriptionLength,
                enabled = vm.isEditable
            )
        }
    }
}