package com.sbcf.pillbox.features.medications.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.sbcf.pillbox.R
import com.sbcf.pillbox.components.SearchField
import com.sbcf.pillbox.features.medications.components.MedicationList
import com.sbcf.pillbox.features.medications.components.MedicationListItemCallbacks
import com.sbcf.pillbox.features.medications.models.MedicationOverview
import com.sbcf.pillbox.features.medications.viewmodels.MedicationListViewModel
import com.sbcf.pillbox.utils.Dimens
import com.sbcf.pillbox.utils.Modifiers.scaffoldedContent

data class MedicationListCallbacks(
    val onAddMedicationClick: () -> Unit,
    val onItemOpenClick: (MedicationOverview) -> Unit,
    val onItemEditClick: (MedicationOverview) -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationListScreen(
    callbacks: MedicationListCallbacks,
    vm: MedicationListViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = vm) {
        vm.fetchMedications()
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            if (!vm.isSearching) {
                Text(stringResource(id = R.string.medication_list_title))
            }
        }, actions = {
            if (vm.isSearching) {
                SearchField(
                    value = vm.textFilter,
                    onValueChange = vm::searchByName,
                    label = {
                        Text(
                            stringResource(id = R.string.search)
                        )
                    },
                    onClear = vm::searchByName,
                    onClose = vm::stopSearching,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.PaddingSmall)
                )
            } else {
                IconButton(onClick = { vm.searchByName() }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search)
                    )
                }
                IconButton(onClick = { callbacks.onAddMedicationClick() }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.add_medication_desc)
                    )
                }
            }
        })
    }) { padding ->
        val modifier = Modifier
            .scaffoldedContent(padding)
            .padding(horizontal = Dimens.PaddingLarge)

        MedicationList(
            medications = vm.medications,
            modifier = modifier,
            itemCallbacksFactory = {
                MedicationListItemCallbacks(
                    onOpen = { callbacks.onItemOpenClick(it) },
                    onEdit = { callbacks.onItemEditClick(it) },
                    onRemoval = { x -> vm.removeMedication(x.id) })
            })
    }
}