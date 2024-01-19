package com.sbcf.pillbox.features.medicationreminders.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.sbcf.pillbox.R
import com.sbcf.pillbox.components.TextInput
import com.sbcf.pillbox.features.medicationreminders.data.DayOfWeek
import com.sbcf.pillbox.features.medicationreminders.viewmodels.MedicationReminderEditViewModel
import com.sbcf.pillbox.utils.Dimens
import com.sbcf.pillbox.utils.Length

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MedicationReminderMainForm(
    state: MedicationReminderEditViewModel.State,
    onTimeButtonClick: () -> Unit,
    time: String,
    dayOfWeekFormatter: (DayOfWeek) -> String
) {
    TextInput(
        state = state.title,
        label = { Text(text = stringResource(id = R.string.medication_reminder)) },
        maxLength = Length.MedicationReminder.MaxTitleLength
    )
    Button(onClick = onTimeButtonClick) {
        Text(
            text = time,
            style = MaterialTheme.typography.displayLarge
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.PaddingNormal)
    ) {

    }
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Dimens.PaddingNormal),
        horizontalArrangement = Arrangement.SpaceAround,
        maxItemsInEachRow = 2
    ) {
        for ((i, toggled) in state.days.withIndex()) {
            FilterChip(
                modifier = Modifier
                    .fillMaxWidth(0.4f),
                selected = toggled,
                onClick = { state.toggleDay(i) },
                label = {
                    val weight = if (toggled) FontWeight.Bold else FontWeight.Normal
                    Text(
                        text = dayOfWeekFormatter(DayOfWeek.days[i]),
                        fontWeight = weight,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                })
        }
    }
}