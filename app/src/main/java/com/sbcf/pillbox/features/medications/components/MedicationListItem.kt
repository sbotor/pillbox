package com.sbcf.pillbox.features.medications.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.sbcf.pillbox.R
import com.sbcf.pillbox.components.ListItemSpacer
import com.sbcf.pillbox.features.medications.models.MedicationOverview
import com.sbcf.pillbox.utils.Dimens

data class MedicationListItemCallbacks(
    val onOpen: (MedicationOverview) -> Unit,
    val onEdit: (MedicationOverview) -> Unit,
    val onRemoval: (MedicationOverview) -> Unit
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MedicationListItem(
    medication: MedicationOverview,
    callbacks: MedicationListItemCallbacks,
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var showRemovalDialog by remember { mutableStateOf(false) }

    if (showRemovalDialog) {
        MedicationRemovalDialog(
            onConfirmation = {
                callbacks.onRemoval(medication)
                showRemovalDialog = false
            },
            onDismissRequest = {
                showRemovalDialog = false
            },
            medication = medication
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { callbacks.onOpen(medication) },
                onLongClick = { menuExpanded = true }
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.PaddingNormal)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.medication_24),
                contentDescription = null,
                modifier = Modifier.padding(end = Dimens.PaddingSmall)
            )
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = medication.name, style = MaterialTheme.typography.headlineSmall)
                Text(text = medication.getDosageString())
            }
        }
        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.edit)) },
                onClick = {
                    callbacks.onEdit(medication)
                    menuExpanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.delete)) },
                onClick = {
                    showRemovalDialog = true
                    menuExpanded = false
                }
            )
        }
    }
    ListItemSpacer()
}