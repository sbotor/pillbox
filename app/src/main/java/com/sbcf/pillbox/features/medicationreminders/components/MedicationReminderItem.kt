package com.sbcf.pillbox.features.medicationreminders.components

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

@Composable
fun MedicationReminderItem(reminder: MedicationReminderOverview) {
    Card(modifier = Modifier.fillMaxWidth()) {
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
            Switch(checked = reminder.isEnabled, onCheckedChange = {})
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