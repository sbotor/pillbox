package com.sbcf.pillbox.features.medicationreminders.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sbcf.pillbox.components.ListItemSpacer
import com.sbcf.pillbox.features.medicationreminders.models.MedicationReminderOverview
import com.sbcf.pillbox.utils.Dimens
import com.sbcf.pillbox.utils.Formatters

data class MedicationReminderItemCallbacks(
    val onClick: (MedicationReminderOverview) -> Unit,
    val onLongClick: (MedicationReminderOverview) -> Unit,
    val onToggle: (MedicationReminderOverview) -> Unit
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MedicationReminderItem(
    reminder: MedicationReminderOverview,
    dateTimeFormatter: (MedicationReminderOverview) -> String,
    callbacks: MedicationReminderItemCallbacks
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(onClick = { callbacks.onClick(reminder) },
                onLongClick = { callbacks.onLongClick(reminder) })
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.PaddingNormal)
        ) {
            Text(
                text = Formatters.time(reminder.hour, reminder.minute),
                style = MaterialTheme.typography.headlineMedium
            )
            if (reminder.isEnabled) {
                Text(text = dateTimeFormatter(reminder))
            }
            Switch(checked = reminder.isEnabled, onCheckedChange = { callbacks.onToggle(reminder) })
        }
        if (reminder.title.isNotEmpty()) {
            Text(text = reminder.title, modifier = Modifier.padding(Dimens.PaddingNormal))
        }

        val setDays = reminder.days.getSetDays()
        if (setDays.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.PaddingNormal)
            ) {
                for (day in setDays) {
                    // TODO: localize this
                    Text(
                        text = day.toString().take(2),
                        modifier = Modifier.padding(horizontal = Dimens.PaddingSmall)
                    )
                }
            }
        }
    }
    ListItemSpacer()
}