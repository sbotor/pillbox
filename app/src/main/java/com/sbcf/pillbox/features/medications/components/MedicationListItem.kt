package com.sbcf.pillbox.features.medications.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sbcf.pillbox.R
import com.sbcf.pillbox.features.medications.models.MedicationOverview
import com.sbcf.pillbox.utils.Dimens

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MedicationListItem(
    medication: MedicationOverview,
    onClick: (MedicationOverview) -> Unit,
    onLongClick: (Int) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var removalAlert by remember { mutableStateOf(false) }

    when{
        removalAlert -> {
            MedicationRemovalDialog(
                onConfirmation = onLongClick,
                onDismissRequest = {removalAlert = false; expanded = false},
                medication = medication)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .combinedClickable(
                onClick = { onClick(medication) },
                onLongClick = { expanded = true }
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.PaddingNormal)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = medication.name, style = MaterialTheme.typography.headlineSmall)
                if (medication.dosage.isNotEmpty()) {
                    Text(text = medication.dosage)
                }
            }
        }

        DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
        ) {
        DropdownMenuItem(
            text = {  Text(stringResource(id = R.string.edit)) },
            onClick = { onClick(medication) }
        )
        DropdownMenuItem(
            text = {  Text(stringResource(id = R.string.delete)) },
            onClick = { removalAlert = true }
        )

    }
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
    )
}