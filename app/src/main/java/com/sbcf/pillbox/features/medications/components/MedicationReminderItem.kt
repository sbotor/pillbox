package com.sbcf.pillbox.features.medications.components

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
import com.sbcf.pillbox.features.medicationreminders.data.ReminderDayMask
import com.sbcf.pillbox.features.medicationreminders.models.MedicationReminderOverview
import com.sbcf.pillbox.utils.Dimens

@Composable
fun MedicationReminderItem() {
    val reminder = MedicationReminderOverview(1, 12, 49, null, ReminderDayMask())

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.PaddingNormal)
        ) {
            Text(
                text = "${reminder.hour}:${reminder.minute}",
                style = MaterialTheme.typography.headlineMedium
            )
            Switch(checked = reminder.isEnabled, onCheckedChange = {})
        }
    }
    ListItemSpacer()
}