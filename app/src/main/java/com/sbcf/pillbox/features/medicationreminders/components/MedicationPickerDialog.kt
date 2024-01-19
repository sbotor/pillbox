package com.sbcf.pillbox.features.medicationreminders.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sbcf.pillbox.components.GeneralDialog
import com.sbcf.pillbox.features.medicationreminders.models.MedicationPickerDialogState
import com.sbcf.pillbox.features.medications.components.MedicationList
import com.sbcf.pillbox.features.medications.components.MedicationListItemCallbacks
import com.sbcf.pillbox.features.medications.models.MedicationOverview
import com.sbcf.pillbox.utils.Dimens

@Composable
fun MedicationPickerDialog(
    state: MedicationPickerDialogState,
    onItemOpen: (MedicationOverview) -> Unit
) {
    GeneralDialog(
        onDismissRequest = { state.hide() },
        modifier = Modifier.fillMaxHeight(0.8f)
    ) {
        MedicationList(
            medications = state.medications,
            itemCallbacksFactory = {
                MedicationListItemCallbacks(onOpen = {
                    onItemOpen(it)
                    state.hide()
                })
            },
            disableItemMenu = true,
            modifier = Modifier.padding(Dimens.PaddingNormal)
        )
    }
}