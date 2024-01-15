package com.sbcf.pillbox.features.medicationreminders.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.sbcf.pillbox.R
import com.sbcf.pillbox.features.medicationreminders.viewmodels.MedicationRemindersViewModel
import com.sbcf.pillbox.features.medications.components.MedicationReminderItem
import com.sbcf.pillbox.utils.Dimens
import com.sbcf.pillbox.utils.Modifiers.scaffoldedContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationRemindersScreen(vm: MedicationRemindersViewModel = hiltViewModel()) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            vm.notificationPermission.setRequestResult(it)
        })

    vm.notificationPermission.check(LocalContext.current)

    Scaffold(topBar = { TopAppBar(title = { }) }) { padding ->
        if (!vm.notificationPermission.isGranted) {
            Column(
                modifier = Modifier.scaffoldedContent(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.notifications_disabled))
                if (!vm.notificationPermission.isEstablished && vm.notificationPermission.shouldRequest()) {
                    Button(onClick = { launcher.launch(Manifest.permission.POST_NOTIFICATIONS) }) {
                        Text(text = stringResource(id = R.string.enable_notifications))
                    }
                }
            }
        } else {
            vm.fetchReminders()
            LazyColumn(
                modifier = Modifier
                    .scaffoldedContent(padding)
                    .padding(horizontal = Dimens.PaddingBig),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(3) {
                    MedicationReminderItem()
                }
            }
        }
    }
}