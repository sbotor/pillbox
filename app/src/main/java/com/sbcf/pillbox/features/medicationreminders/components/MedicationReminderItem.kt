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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sbcf.pillbox.components.ListItemSpacer
import com.sbcf.pillbox.features.medicationreminders.data.DayOfWeek
import com.sbcf.pillbox.features.medicationreminders.models.MedicationReminderOverview
import com.sbcf.pillbox.utils.Dimens

data class MedicationReminderItemCallbacks(
    val onClick: (MedicationReminderOverview) -> Unit,
    val onRemoval: (MedicationReminderOverview) -> Unit,
    val onToggle: (MedicationReminderOverview) -> Unit
)

data class MedicationReminderItemFormatters(
    val nextNotificationTime: (MedicationReminderOverview) -> String,
    val displayLabel: (MedicationReminderOverview) -> String,
    val shortDayOfWeek: (DayOfWeek) -> String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MedicationReminderItem(
    reminder: MedicationReminderOverview,
    displayTime: String,
    formatters: MedicationReminderItemFormatters,
    callbacks: MedicationReminderItemCallbacks
) {
    var showRemovalDialog by remember { mutableStateOf(false) }

    if (showRemovalDialog) {
        MedicationReminderRemovalDialog(
            onConfirmation = {
                callbacks.onRemoval(reminder)
                showRemovalDialog = false
            },
            onDismissRequest = {
                showRemovalDialog = false
            },
            subtitle = formatters.displayLabel(reminder)
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(onClick = { callbacks.onClick(reminder) },
                onLongClick = { showRemovalDialog = true })
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.PaddingNormal)
        ) {
            Text(
                text = displayTime,
                style = MaterialTheme.typography.headlineMedium
            )
            if (reminder.isEnabled) {
                Text(text = formatters.nextNotificationTime(reminder))
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
                    Text(
                        text = formatters.shortDayOfWeek(day),
                        modifier = Modifier.padding(horizontal = Dimens.PaddingSmall)
                    )
                }
            }
        }
    }
    ListItemSpacer()
}