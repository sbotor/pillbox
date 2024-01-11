package com.sbcf.pillbox.features.medications.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sbcf.pillbox.features.medications.viewmodels.MedicationsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sbcf.pillbox.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationList(
    medicationsViewModel: MedicationsViewModel = viewModel()
) {
    medicationsViewModel.fetchMedications()
    val medications by medicationsViewModel.medications.collectAsState()

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(stringResource(id = R.string.medications_list_title))
        }, actions = {
            IconButton(onClick = { print("new medication clicked") }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.add_medication_desc)
                )
            }
        })
    }) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            items(medications) {
                MedicationOverview(medication = it)
            }
        }
    }
}