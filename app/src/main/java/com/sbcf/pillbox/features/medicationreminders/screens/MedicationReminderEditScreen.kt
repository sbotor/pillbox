package com.sbcf.pillbox.features.medicationreminders.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sbcf.pillbox.R
import com.sbcf.pillbox.components.TextInput
import com.sbcf.pillbox.features.medicationreminders.data.DayOfWeek
import com.sbcf.pillbox.features.medicationreminders.viewmodels.MedicationReminderEditViewModel
import com.sbcf.pillbox.utils.Dimens
import com.sbcf.pillbox.utils.Length
import com.sbcf.pillbox.utils.Modifiers.scaffoldedContent

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EditMedicationReminderScreen(
    vm: MedicationReminderEditViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialHour = vm.state.hour,
        initialMinute = vm.state.minute,
        is24Hour = true
    )

    if (vm.showTimePicker) {
        AlertDialog(
            onDismissRequest = { vm.showTimePicker = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(size = 12.dp)
                ),
        ) {
            Column(
                modifier = Modifier.padding(Dimens.PaddingBig),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(state = timePickerState)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.PaddingNormal),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { vm.showTimePicker = false }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Button(onClick = {
                        vm.state.setTime(timePickerState.hour, timePickerState.minute)
                        vm.showTimePicker = false
                    }) {
                        Text(text = stringResource(id = R.string.ok))
                    }
                }
            }
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = { /*TODO*/ Text(text = "Dodaj przypomnienie") }, navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        }, actions = {
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
        Column(
            modifier = Modifier
                .scaffoldedContent(padding)
                .padding(horizontal = Dimens.PaddingNormal),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextInput(
                state = vm.state.title,
                label = { /*TODO*/ Text(text = "Tytuł") },
                maxLength = Length.MedicationReminder.MaxTitleLength
            )
            Button(onClick = { vm.showTimePicker = true }) {
                Text(
                    text = vm.formatTime(),
                    style = MaterialTheme.typography.displayLarge
                )
            }
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.PaddingNormal),
                horizontalArrangement = Arrangement.Center
            ) {
                for ((i, toggled) in vm.state.days.withIndex()) {
                    FilterChip(
                        modifier = Modifier.padding(horizontal = Dimens.PaddingNormal),
                        selected = toggled,
                        onClick = { vm.state.toggleDay(i) },
                        label = {
                            val weight = if (toggled) FontWeight.Bold else FontWeight.Normal
                            Text(
                                text = vm.formatDayOfWeek(DayOfWeek.days[i]),
                                fontWeight = weight
                            )
                        })
                }
            }
        }
    }
}