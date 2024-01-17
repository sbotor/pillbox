package com.sbcf.pillbox.features.home.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sbcf.pillbox.R
import com.sbcf.pillbox.features.home.components.NextMedicationReminderItem
import com.sbcf.pillbox.features.home.viewmodels.HomeViewModel
import com.sbcf.pillbox.utils.Dimens
import com.sbcf.pillbox.utils.Modifiers.scaffoldedContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    vm: HomeViewModel = hiltViewModel(),
    onNextReminderClick: () -> Unit,
    onUnreadReminderClick: (/*TODO: type*/) -> Unit,
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
        Column(modifier = Modifier
            .scaffoldedContent(padding)
            .padding(horizontal = Dimens.PaddingBig)
        ) {
            Row(modifier = Modifier.fillMaxHeight(0.3F))
            {
                Column {
                    Text(
                        text = stringResource(id = R.string.next_reminder),
                        style = MaterialTheme.typography.titleLarge
                    )
                    NextMedicationReminderItem(
                        reminder = vm.reminder,
                        onClick = onNextReminderClick,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = Dimens.PaddingBig),
                        datetimeFormatter = { vm.formatNextNotificationTime(it) }
                    )
                }
            }


            Row(modifier = Modifier.padding(top = 20.dp))
            {
                Column {
                    Text(
                        text = stringResource(id = R.string.missed_reminders),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(text = "Todo")
                }

            }
        }
    }
}