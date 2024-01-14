package com.sbcf.pillbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sbcf.pillbox.features.medications.services.MedicationNotificationPublisher
import com.sbcf.pillbox.screens.PillBoxApp
import com.sbcf.pillbox.ui.theme.PillBoxTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var notificationPublisher: MedicationNotificationPublisher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notificationPublisher.ensureChannel()

        setContent {
            PillBoxTheme {
                PillBoxApp()
                // TODO: Used for notification testing
                //MedicationNotificationsScreen()
            }
        }
    }
}
