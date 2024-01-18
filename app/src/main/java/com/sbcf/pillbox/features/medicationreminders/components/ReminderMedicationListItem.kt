package com.sbcf.pillbox.features.medicationreminders.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sbcf.pillbox.components.ListItemSpacer
import com.sbcf.pillbox.utils.Dimens

@Composable
fun ReminderMedicationListItem(
    name: String,
    actions: @Composable (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.PaddingNormal),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = name, modifier = Modifier.padding(Dimens.PaddingNormal))
            if (actions != null) {
                Box(modifier = Modifier.padding(Dimens.PaddingNormal)) {
                    actions()
                }
            }
        }
    }
    ListItemSpacer(height = Dimens.PaddingSmall)
}