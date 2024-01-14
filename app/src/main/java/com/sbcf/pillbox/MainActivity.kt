package com.sbcf.pillbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.sbcf.pillbox.features.medications.data.MedicationNotificationsRepository
import com.sbcf.pillbox.features.medications.services.MedicationAlarmScheduler
import com.sbcf.pillbox.screens.PillBoxApp
import com.sbcf.pillbox.ui.theme.PillBoxTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var scheduler: MedicationAlarmScheduler
    @Inject
    lateinit var repo: MedicationNotificationsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val n = repo.getDue(0)
            scheduler.scheduleAll(n)
        }

        setContent {
            PillBoxTheme {
                PillBoxApp()
            }
        }
    }
}
