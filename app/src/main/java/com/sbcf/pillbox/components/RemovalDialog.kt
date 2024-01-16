package com.sbcf.pillbox.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sbcf.pillbox.R

@Composable
fun RemovalDialog(
    onConfirmation: () -> Unit,
    onDismissRequest: () -> Unit,
    title: String,
    subtitle: String,
    content: String
) {
    AlertDialog(
        title = {
            Text(text = title)
        },
        text = {
            Column {
                Text(text = subtitle, style = MaterialTheme.typography.titleMedium)
                Text(text = content)
            }
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