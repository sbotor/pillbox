package com.sbcf.pillbox.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sbcf.pillbox.components.BottomNavBar
import com.sbcf.pillbox.features.home.HomeScreens
import com.sbcf.pillbox.features.home.home
import com.sbcf.pillbox.features.medicationreminders.medicationReminders
import com.sbcf.pillbox.features.medications.medications
import com.sbcf.pillbox.features.reminderhistory.reminderHistory
import com.sbcf.pillbox.utils.Modifiers.scaffoldedContent

@Composable
fun PillBoxApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        }
    )
    { padding ->
        NavHost(
            navController = navController,
            startDestination = HomeScreens.Home.route,
            modifier = Modifier.scaffoldedContent(padding)
        ) {
            home(navController)
            medications(navController)
            medicationReminders(navController)
            reminderHistory(navController)
        }
    }
}