package com.sbcf.pillbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sbcf.pillbox.features.medicationreminders.services.MedicationReminderPublisher
import com.sbcf.pillbox.features.reminderhistory.ReminderHistoryScreens
import com.sbcf.pillbox.screens.PillBoxApp
import com.sbcf.pillbox.ui.theme.PillBoxTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var reminderPublisher: MedicationReminderPublisher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reminderPublisher.ensureChannel()

        setContent {
            PillBoxTheme {
                PillBoxApp()
            }
        }
    }
}
