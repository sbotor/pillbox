package com.sbcf.pillbox.features.medicationreminders.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sbcf.pillbox.R
import com.sbcf.pillbox.components.GeneralDialog
import com.sbcf.pillbox.features.medicationreminders.models.ReminderTimeState
import com.sbcf.pillbox.utils.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderTimePickerDialog(
    state: ReminderTimeState
) {
    val timePickerState = rememberTimePickerState(
        initialHour = state.hour,
        initialMinute = state.minute,
        is24Hour = true
    )

    GeneralDialog(onDismissRequest = { state.showTimePicker = false }) {
        Column(
            modifier = Modifier.padding(Dimens.PaddingLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimePicker(state = timePickerState)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.PaddingNormal),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { state.showTimePicker = false }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                Button(onClick = {
                    state.hour = timePickerState.hour
                    state.minute = timePickerState.minute
                    state.showTimePicker = false
                }) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        }
    }
}