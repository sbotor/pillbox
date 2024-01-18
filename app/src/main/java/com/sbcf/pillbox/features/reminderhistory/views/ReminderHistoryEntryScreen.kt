package com.sbcf.pillbox.features.reminderhistory.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sbcf.pillbox.R
import com.sbcf.pillbox.components.ListItemSpacer
import com.sbcf.pillbox.features.medicationreminders.components.ReminderMedicationListItem
import com.sbcf.pillbox.features.reminderhistory.viewmodels.ReminderHistoryEntryViewModel
import com.sbcf.pillbox.utils.Dimens
import com.sbcf.pillbox.utils.Modifiers.scaffoldedContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderHistoryEntryScreen(
    entryId: Int,
    vm: ReminderHistoryEntryViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    LaunchedEffect(key1 = vm, key2 = entryId) {
        vm.fetchEntryAndMarkAsViewed(entryId)
    }
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.reminder_history_long_name))
            },
            navigationIcon = {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            },
        )
    }) { padding ->
        Column(
            modifier = Modifier
                .scaffoldedContent(padding)
                .padding(horizontal = Dimens.PaddingNormal),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = vm.formattedDeliveryDate,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Text(
                text = vm.formattedDeliveryTime,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            if (vm.description.isNotEmpty()) {
                Text(
                    text = vm.description,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = Dimens.PaddingNormal),
                    textAlign = TextAlign.Center
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.PaddingNormal)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.PaddingNormal),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.reminder_history_confirm))
                    Switch(checked = vm.isConfirmed, onCheckedChange = { vm.toggleConfirmation() })
                }
            }
            if (vm.items.isNotEmpty()) {
                Text(
                    text = stringResource(id = R.string.reminder_history_medications),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = Dimens.PaddingLarge)
                )
                LazyColumn {
                    items(vm.items) {
                        ReminderMedicationListItem(name = it)
                    }
                }
            }
        }
    }
}