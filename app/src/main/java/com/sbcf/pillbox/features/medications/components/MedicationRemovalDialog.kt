package com.sbcf.pillbox.features.medications.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sbcf.pillbox.R
import com.sbcf.pillbox.features.medications.models.MedicationOverview

@Composable
fun MedicationRemovalDialog(
    onConfirmation: () -> Unit,
    onDismissRequest: () -> Unit,
    medication: MedicationOverview
) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.medication_removal))
        },
        text = {
            // TODO
            Text(text = "Czy na pewno chcesz usunąć lek '${medication.name}'?")
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = onConfirmation
            ) {
                Text(stringResource(id = R.string.delete))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}