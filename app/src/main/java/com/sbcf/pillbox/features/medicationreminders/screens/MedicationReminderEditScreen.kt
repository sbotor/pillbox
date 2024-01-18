package com.sbcf.pillbox.features.medicationreminders.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
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
import com.sbcf.pillbox.features.medicationreminders.components.MedicationPickerDialog
import com.sbcf.pillbox.features.medicationreminders.components.MedicationReminderMainForm
import com.sbcf.pillbox.features.medicationreminders.components.ReminderMedicationsList
import com.sbcf.pillbox.features.medicationreminders.components.ReminderTimePickerDialog
import com.sbcf.pillbox.features.medicationreminders.viewmodels.MedicationReminderEditViewModel
import com.sbcf.pillbox.utils.Dimens
import com.sbcf.pillbox.utils.Modifiers.scaffoldedContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMedicationReminderScreen(
    vm: MedicationReminderEditViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    reminderId: Int? = null
) {
    LaunchedEffect(key1 = vm, key2 = reminderId) {
        vm.fetchReminder(reminderId)
    }

    LaunchedEffect(key1 = vm, key2 = vm.state.medPicker.isShown) {
        if (vm.state.medPicker.isShown) {
            vm.fetchMedications()
        }
    }

    if (vm.state.time.showTimePicker) {
        ReminderTimePickerDialog(state = vm.state.time)
    }

    if (vm.state.medPicker.isShown) {
        MedicationPickerDialog(state = vm.state.medPicker, onItemOpen = { vm.addMed(it) })
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.medication_reminder)) },
            navigationIcon = {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            },
            actions = {
                Button(onClick = {
                    vm.save()
                    onBackClick()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = stringResource(id = R.string.save)
                    )
                    Text(text = stringResource(id = R.string.save))
                }
            })
    }) { padding ->
        LazyColumn(
            modifier = Modifier
                .scaffoldedContent(padding)
                .padding(horizontal = Dimens.PaddingNormal),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                MedicationReminderMainForm(
                    state = vm.state,
                    onTimeButtonClick = { vm.state.time.showTimePicker = true },
                    time = vm.state.time.toString(),
                    dayOfWeekFormatter = { vm.formatDayOfWeek(it) }
                )
            }
            item {
                ReminderMedicationsList(
                    meds = vm.state.medications,
                    onAddItemClick = { vm.state.medPicker.show() },
                    onRemoveItemClick = { vm.removeMed(it.id) })
            }
        }
    }
}