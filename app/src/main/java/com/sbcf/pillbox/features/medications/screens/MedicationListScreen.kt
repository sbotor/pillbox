package com.sbcf.pillbox.features.medications.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.sbcf.pillbox.R
import com.sbcf.pillbox.components.SearchField
import com.sbcf.pillbox.features.medications.components.MedicationListItem
import com.sbcf.pillbox.features.medications.components.MedicationListItemCallbacks
import com.sbcf.pillbox.features.medications.models.MedicationOverview
import com.sbcf.pillbox.features.medications.viewmodels.MedicationListViewModel
import com.sbcf.pillbox.utils.Dimens
import com.sbcf.pillbox.utils.Modifiers.scaffoldedContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationListScreen(
    onAddMedicationClick: () -> Unit,
    onItemOpenClick: (MedicationOverview) -> Unit,
    onItemEditClick: (MedicationOverview) -> Unit,
    vm: MedicationListViewModel = hiltViewModel(),
) {
    vm.fetchMedications()

    Scaffold(topBar = {
        TopAppBar(title = {
            if (!vm.isSearching) {
                Text(stringResource(id = R.string.medications_list_title))
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
                IconButton(onClick = onAddMedicationClick) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.add_medication_desc)
                    )
                }
            }
        })
    }) { padding ->
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.scaffoldedContent(padding)
        ) {
            items(vm.medications) {
                val callbacks = MedicationListItemCallbacks(
                    onOpen = onItemOpenClick,
                    onEdit = onItemEditClick,
                    onRemoval = { vm.removeMedication(it.id) })
                MedicationListItem(medication = it, callbacks = callbacks)
            }
        }
    }
}