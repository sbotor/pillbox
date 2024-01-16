package com.sbcf.pillbox.features.medications.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sbcf.pillbox.R
import com.sbcf.pillbox.components.RemovalDialog
import com.sbcf.pillbox.features.medications.models.MedicationOverview

@Composable
fun MedicationRemovalDialog(
    onConfirmation: () -> Unit,
    onDismissRequest: () -> Unit,
    medication: MedicationOverview
) {
    RemovalDialog(
        onConfirmation = onConfirmation,
        onDismissRequest = onDismissRequest,
        title = stringResource(id = R.string.medication_removal_title),
        subtitle = medication.name,
        content = stringResource(id = R.string.medication_removal_message)
    )
}