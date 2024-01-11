package com.sbcf.pillbox.features.medications.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sbcf.pillbox.features.medications.data.Medication

@Composable
fun MedicationOverview(medication: Medication) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(60.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = medication.name, fontSize = 24.sp)
        }
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
    )
}