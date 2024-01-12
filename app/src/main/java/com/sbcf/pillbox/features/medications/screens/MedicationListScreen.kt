package com.sbcf.pillbox.features.medications.screens

import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import com.sbcf.pillbox.features.medications.viewmodels.MedicationListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sbcf.pillbox.R
import com.sbcf.pillbox.components.SearchField
import com.sbcf.pillbox.features.medications.components.MedicationListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationListScreen(
    vm: MedicationListViewModel = viewModel()
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
                        .padding(5.dp)
                )
            } else {
                IconButton(onClick = { vm.searchByName() }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search)
                    )
                }
                IconButton(onClick = { }) {
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
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(vm.medications) {
                MedicationListItem(medication = it)
            }
        }
    }
}