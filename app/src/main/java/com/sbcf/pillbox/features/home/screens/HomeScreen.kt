package com.sbcf.pillbox.features.home.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sbcf.pillbox.R
import com.sbcf.pillbox.components.ListItemSpacer
import com.sbcf.pillbox.features.home.components.NextMedicationReminderItem
import com.sbcf.pillbox.features.home.viewmodels.HomeViewModel
import com.sbcf.pillbox.features.reminderhistory.components.ReminderHistoryListItem
import com.sbcf.pillbox.utils.Dimens
import com.sbcf.pillbox.utils.Modifiers.scaffoldedContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    vm: HomeViewModel = hiltViewModel(),
    onNextReminderClick: () -> Unit,
    onUnreadReminderClick: (Int) -> Unit,
    onHistoryClick: () -> Unit
) {
    LaunchedEffect(key1 = vm, block = {
        vm.fetchInformation()
    })

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(stringResource(id = R.string.home_page_title))
            }
        )
    })
    { padding ->

        LazyColumn(
            modifier = Modifier
                .scaffoldedContent(padding)
                .padding(horizontal = Dimens.PaddingLarge)
        ) {
            item {
                Row {
                    Column {
                        Text(
                            text = stringResource(id = R.string.next_reminder),
                            style = MaterialTheme.typography.headlineMedium
                        )
                        NextMedicationReminderItem(
                            reminder = vm.reminder,
                            onClick = onNextReminderClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = Dimens.PaddingNormal,
                                    vertical = Dimens.PaddingLarge
                                ),
                            datetimeFormatter = { vm.formatNextNotificationTime(it) }
                        )
                    }
                }
            }
            item {
                Row(modifier = Modifier.padding(vertical = Dimens.PaddingLarge)) {
                    Column {
                        Text(
                            text = stringResource(id = R.string.missed_reminders),
                            style = MaterialTheme.typography.headlineMedium,
                        )
                    }
                }
            }

            if (vm.history.isNotEmpty()) {
                items(vm.history) { rem ->
                    ReminderHistoryListItem(
                        reminder = rem,
                        onClick = onUnreadReminderClick,
                        datetimeFormatter = { vm.formatNextNotificationTime(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = Dimens.PaddingNormal,
                                //vertical = Dimens.PaddingLarge
                            )
                    )
                }
            } else {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimens.PaddingLarge)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Dimens.PaddingNormal),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = "Jesteś na bieżąco", //TODO: stringXML
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    ListItemSpacer()
                }
            }

            item {
                Button(
                    onClick = onHistoryClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Dimens.PaddingLarge)
                        .padding(horizontal = 40.dp),
                ) {
                    Text(
                        text = if (vm.history.isEmpty()) "Pokaż historię" else "Pokaż więcej",//TODO: stringXML
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}