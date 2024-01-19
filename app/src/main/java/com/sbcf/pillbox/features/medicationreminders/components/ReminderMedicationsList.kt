package com.sbcf.pillbox.features.medicationreminders.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sbcf.pillbox.R
import com.sbcf.pillbox.features.medicationreminders.models.ReminderMedicationItem

@Composable
fun ReminderMedicationsList(
    meds: List<ReminderMedicationItem>,
    onAddItemClick: () -> Unit,
    onRemoveItemClick: (ReminderMedicationItem) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            val titlePrefix = stringResource(id = R.string.medication_reminder_medications)
            Text(
                text = "$titlePrefix (${meds.size})",
                style = MaterialTheme.typography.titleMedium
            )
            ElevatedButton(onClick = onAddItemClick) {
                Text(text = stringResource(id = R.string.add))
            }
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            for (med in meds) {
                ReminderMedicationListItem(
                    name = med.name,
                    dosage = "${med.dosageAmount} ${med.dosageUnit}"
                ) {
                    TextButton(onClick = { onRemoveItemClick(med) }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(id = R.string.delete)
                        )
                        Text(text = stringResource(id = R.string.delete))
                    }
                }
            }
        }
    }
}