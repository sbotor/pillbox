package com.sbcf.pillbox.features.medications.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sbcf.pillbox.features.medications.models.MedicationOverview

@Composable
fun MedicationListItem(medication: MedicationOverview) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .heightIn(min = 60.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = medication.name, fontSize = 24.sp)
                if (medication.dosage.isNotEmpty()) {
                    Text(text = medication.dosage)
                }
            }
        }
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
    )
}