package com.sbcf.pillbox.features.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.style.TextAlign
import com.sbcf.pillbox.R
import com.sbcf.pillbox.features.medicationreminders.data.MedicationReminder
import com.sbcf.pillbox.utils.Dimens

@Composable
fun NextMedicationReminderItem(
    reminder: MedicationReminder?,
    onClick: () -> Unit,
    modifier: Modifier,
    datetimeFormatter: (Long?) -> String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        if (reminder == null) {
            Column(
                modifier = Modifier
                    .padding(Dimens.PaddingNormal),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    stringResource(id = R.string.no_set_reminders),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .padding(Dimens.PaddingNormal)
            ) {
                Text(
                    text = datetimeFormatter(reminder.nextDeliveryTimestamp),
                    style = MaterialTheme.typography.headlineSmall,
                )
            }

            if (reminder.title.isNotEmpty()) {
                Text(
                    text = reminder.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(
                        start = Dimens.PaddingNormal,
                        end = Dimens.PaddingNormal,
                        bottom = Dimens.PaddingNormal
                    )
                )
            }

            Row {
                Text(
                    text = "TODO: Tutaj mała lista leków.",
                    modifier = Modifier.padding(
                        start = Dimens.PaddingNormal,
                        end = Dimens.PaddingNormal,
                        bottom = Dimens.PaddingNormal
                    )
                )
            }
        }

    }


}