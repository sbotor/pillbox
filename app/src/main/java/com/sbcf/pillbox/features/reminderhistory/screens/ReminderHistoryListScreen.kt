package com.sbcf.pillbox.features.reminderhistory.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.sbcf.pillbox.R
import com.sbcf.pillbox.features.reminderhistory.components.ReminderHistoryListItem
import com.sbcf.pillbox.features.reminderhistory.viewmodels.ReminderHistoryViewModel
import com.sbcf.pillbox.utils.Dimens
import com.sbcf.pillbox.utils.Modifiers.scaffoldedContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderHistoryListScreen(
    vm: ReminderHistoryViewModel = hiltViewModel(),
    onItemClick: (id: Int) -> Unit
) {
    LaunchedEffect(key1 = vm) {
        vm.fetchReminders()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.history_page_title)) },
            )
        }
    ) { padding ->
        if (vm.reminders.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .scaffoldedContent(padding)
                    .padding(horizontal = Dimens.PaddingLarge)
            ) {
                Text(text = stringResource(id = R.string.medication_reminder_list_empty))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .scaffoldedContent(padding)
                    .padding(horizontal = Dimens.PaddingLarge),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(vm.reminders) { rem ->
                    ReminderHistoryListItem(
                        reminder = rem,
                        onClick = { it ->
                            if (!rem.data.wasViewed)
                                vm.markReminderAsViewed(it)
                            onItemClick(it)
                        },
                        datetimeFormatter = { vm.formattedDelivery(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }


}