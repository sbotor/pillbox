package com.sbcf.pillbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.sbcf.pillbox.features.medications.components.MedicationList
import com.sbcf.pillbox.features.medications.data.Medication
import com.sbcf.pillbox.features.medications.viewmodels.MedicationsViewModel
import com.sbcf.pillbox.ui.theme.PillBoxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PillBoxTheme {
                val viewModel: MedicationsViewModel by viewModels()
                MedicationList(
                    medications = listOf(
                        Medication(1, "med1"),
                        Medication(2, "med2"),
                        Medication(3, "med3")
                    )
                )
            }
        }
    }
}
