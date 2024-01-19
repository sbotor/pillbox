package com.sbcf.pillbox.features.medications.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sbcf.pillbox.R
import com.sbcf.pillbox.features.medications.models.MedicationOverview

@Composable
fun MedicationList(
    medications: List<MedicationOverview>,
    itemCallbacksFactory: (MedicationOverview) -> MedicationListItemCallbacks,
    modifier: Modifier = Modifier,
    disableItemMenu: Boolean = false,
) {
    val alignment = Alignment.CenterHorizontally

    if (medications.isEmpty()) {
        Column(
            horizontalAlignment = alignment,
            modifier = modifier
        ) {
            Text(text = stringResource(id = R.string.medication_list_empty))
        }
    } else {
        LazyColumn(
            horizontalAlignment = alignment,
            modifier = modifier
        ) {
            items(medications) { med ->
                val callbacks = itemCallbacksFactory(med)
                MedicationListItem(
                    medication = med,
                    callbacks = callbacks,
                    disableMenu = disableItemMenu
                )
            }
        }
    }
}