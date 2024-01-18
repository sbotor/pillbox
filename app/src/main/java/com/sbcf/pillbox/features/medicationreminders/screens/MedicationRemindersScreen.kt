package com.sbcf.pillbox.features.medicationreminders.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sbcf.pillbox.R
import com.sbcf.pillbox.features.medicationreminders.components.MedicationReminderItem
import com.sbcf.pillbox.features.medicationreminders.components.MedicationReminderItemCallbacks
import com.sbcf.pillbox.features.medicationreminders.components.MedicationReminderItemFormatters
import com.sbcf.pillbox.features.medicationreminders.models.MedicationReminderOverview
import com.sbcf.pillbox.features.medicationreminders.viewmodels.MedicationRemindersViewModel
import com.sbcf.pillbox.utils.Dimens
import com.sbcf.pillbox.utils.Modifiers.scaffoldedContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationRemindersScreen(
    vm: MedicationRemindersViewModel = hiltViewModel(),
    onAddClick: () -> Unit,
    onItemClick: (MedicationReminderOverview) -> Unit,
    onHistoryClick: () -> Unit
) {
    val notificationPermLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            vm.notificationPermission.setRequestResult(it)
        })

    vm.notificationPermission.check(LocalContext.current)
    
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.medication_reminder_list_title)) },
            actions = {
                if (vm.notificationPermission.isGranted) {
                    IconButton(onClick = onAddClick) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(id = R.string.add_medication_reminder_desc)
                        )
                    }
                }
            })
    }) { padding ->
        if (!vm.notificationPermission.isGranted) {
            Column(
                modifier = Modifier.scaffoldedContent(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.notifications_disabled))
                if (!vm.notificationPermission.isEstablished && vm.notificationPermission.shouldRequest()) {
                    Button(onClick = { notificationPermLauncher.launch(Manifest.permission.POST_NOTIFICATIONS) }) {
                        Text(text = stringResource(id = R.string.enable_notifications))
                    }
                }
            }
        } else {
            MedicationReminderList(padding = padding, onItemClick = onItemClick, onHistoryClick = onHistoryClick)
        }
    }
}

@Composable
private fun MedicationReminderList(
    padding: PaddingValues,
    vm: MedicationRemindersViewModel = hiltViewModel(),
    onItemClick: (MedicationReminderOverview) -> Unit,
    onHistoryClick: () -> Unit
) {
    LaunchedEffect(key1 = vm) {
        vm.fetchReminders()
    }

    Column(modifier = Modifier.scaffoldedContent(padding))
    {

        val alignment = Alignment.CenterHorizontally
        val modifier = Modifier
            .padding(horizontal = Dimens.PaddingLarge)

        Column(modifier = modifier.fillMaxWidth(),
            horizontalAlignment = alignment,
        ) {
            Button(
                onClick = onHistoryClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            ) {
                Text(text = "Historia", fontSize = 25.sp) //TODO
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        )

        if (vm.reminders.isEmpty()) {
            Column(
                horizontalAlignment = alignment,
                modifier = modifier
            ) {
                Text(text = stringResource(id = R.string.medication_reminder_list_empty))
            }
        } else {
            LazyColumn(
                modifier = modifier,
                horizontalAlignment = alignment
            ) {
                items(vm.reminders) { x ->
                    val callbacks = MedicationReminderItemCallbacks(
                        onClick = onItemClick,
                        onRemoval = { vm.removeReminder(x.id) },
                        onToggle = { vm.toggleReminder(x) }
                    )
                    val formatters = MedicationReminderItemFormatters(
                        nextNotificationTime = { vm.formatNextNotificationTime(it) },
                        displayLabel = { vm.formatReminderDisplayLabel(it) },
                        shortDayOfWeek = { vm.formatShortDayOfWeek(it) }
                    )
                    MedicationReminderItem(
                        reminder = x,
                        callbacks = callbacks,
                        displayTime = vm.formatReminderDisplayTime(x),
                        formatters = formatters
                    )
                }
            }
        }
    }
}