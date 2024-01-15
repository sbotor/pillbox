package com.sbcf.pillbox.features.medicationreminders.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
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
import com.sbcf.pillbox.components.TextInput
import com.sbcf.pillbox.features.medicationreminders.viewmodels.MedicationReminderEditViewModel
import com.sbcf.pillbox.utils.Dimens
import com.sbcf.pillbox.utils.Length
import com.sbcf.pillbox.utils.Modifiers.scaffoldedContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMedicationReminderScreen(
    vm: MedicationReminderEditViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(title = { /*TODO*/ Text(text = "Dodaj przypomnienie") }, navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        }, actions = {
            Button(onClick = {
                vm.save()
                onBackClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(id = R.string.save)
                )
                Text(text = stringResource(id = R.string.save))
            }
        })
    }) { padding ->
        Column(
            modifier = Modifier
                .scaffoldedContent(padding)
                .padding(horizontal = Dimens.PaddingNormal),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextInput(
                state = vm.state.title,
                label = { /*TODO*/ Text(text = "Tytuł") },
                maxLength = Length.MedicationReminder.MaxTitleLength
            )
        }
    }
}