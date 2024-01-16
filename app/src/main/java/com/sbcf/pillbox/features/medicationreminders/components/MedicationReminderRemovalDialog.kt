package com.sbcf.pillbox.features.medicationreminders.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sbcf.pillbox.R
import com.sbcf.pillbox.components.RemovalDialog

@Composable
fun MedicationReminderRemovalDialog(
    onConfirmation: () -> Unit,
    onDismissRequest: () -> Unit,
    subtitle: String
) {
    RemovalDialog(
        onConfirmation = onConfirmation,
        onDismissRequest = onDismissRequest,
        title = stringResource(id = R.string.medication_reminder_removal_title),
        subtitle = subtitle,
        content = stringResource(id = R.string.medication_reminder_removal_message)
    )
}