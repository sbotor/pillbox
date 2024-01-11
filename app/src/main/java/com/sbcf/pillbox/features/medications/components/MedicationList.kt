package com.sbcf.pillbox.features.medications.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.sbcf.pillbox.features.medications.data.Medication

@Composable
fun MedicationList(medications: List<Medication>) {
    LazyColumn(content = {
        items(medications) {x ->
            Text(x.name)
        }
    })
}