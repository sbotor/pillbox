package com.sbcf.pillbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sbcf.pillbox.features.medications.screens.MedicationFormScreen
import com.sbcf.pillbox.features.medications.screens.MedicationListScreen
import com.sbcf.pillbox.ui.theme.PillBoxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PillBoxTheme {
                MedicationListScreen()
                //MedicationFormScreen()
            }
        }
    }
}
