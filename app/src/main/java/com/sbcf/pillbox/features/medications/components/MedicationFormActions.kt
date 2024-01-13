package com.sbcf.pillbox.features.medications.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sbcf.pillbox.R

@Composable
fun MedicationFormActions(
    isCreating: Boolean,
    isEditable: Boolean,
    onSaveClick: () -> Unit,
    onEditToggle: () -> Unit = {},
) {
    if (isCreating) {
        Button(onClick = onSaveClick) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = stringResource(id = R.string.add_medication)
            )
            Text(text = stringResource(id = R.string.add_medication))
        }
    } else if (isEditable) {
        IconButton(onClick = onEditToggle) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = stringResource(id = R.string.cancel)
            )
        }
        Button(onClick = onSaveClick) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = stringResource(id = R.string.save)
            )
            Text(text = stringResource(id = R.string.save))
        }
    } else {
        Button(onClick = onEditToggle) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = stringResource(id = R.string.edit)
            )
            Text(text = stringResource(id = R.string.edit))
        }
    }
}