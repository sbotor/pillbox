package com.sbcf.pillbox.features.reminderhistory.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sbcf.pillbox.R
import com.sbcf.pillbox.components.ListItemSpacer
import com.sbcf.pillbox.features.reminderhistory.data.ReminderHistoryEntry
import com.sbcf.pillbox.utils.Dimens

@Composable
fun ReminderHistoryListItem(
    reminder: ReminderHistoryEntry,
    onClick: (id: Int) -> Unit,
    formatted: (Long) -> String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(reminder.data.id) },
        border =
            if (!reminder.data.isConfirmed) BorderStroke(1.dp, MaterialTheme.colorScheme.error)
            else null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.PaddingNormal)
        ) {

            Text(
                text = formatted(reminder.data.deliveredTimestamp),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = if (!reminder.data.wasViewed) FontWeight.Bold else FontWeight.Normal
            )
            if (reminder.data.description.isNotEmpty()) {
                Text(
                    text = reminder.data.description,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = if (!reminder.data.wasViewed) FontWeight.Bold else FontWeight.Normal
                )
            }
            if (!reminder.data.isConfirmed) {
                Text(
                    text = stringResource(id = R.string.not_taken_medicine),
                    modifier = Modifier
                        .padding(Dimens.PaddingNormal)
                        .align(Alignment.End),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
    ListItemSpacer()
}