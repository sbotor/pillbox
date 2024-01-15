package com.sbcf.pillbox.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sbcf.pillbox.components.BottomNavBar
import com.sbcf.pillbox.features.home.screens.HomeScreen
import com.sbcf.pillbox.features.medicationreminders.screens.EditMedicationReminderScreen
import com.sbcf.pillbox.features.medications.screens.AddMedicationScreen
import com.sbcf.pillbox.features.medications.screens.MedicationDetailsScreen
import com.sbcf.pillbox.features.medications.screens.MedicationListScreen
import com.sbcf.pillbox.features.medicationreminders.screens.MedicationRemindersScreen
import com.sbcf.pillbox.features.medications.screens.MedicationDetailsCallbacks
import com.sbcf.pillbox.features.medications.screens.MedicationListCallbacks
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
            startDestination = Screen.Home.route,
            modifier = Modifier.scaffoldedContent(padding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen()
            }

            composable(Screen.MedicationList.route) {
                val callbacks = MedicationListCallbacks(
                    onAddMedicationClick = {
                        navController.navigate(Screen.AddMedication.route)
                    },
                    onItemOpenClick = {
                        navController.navigate(Screen.MedicationDetails.createRoute(it.id, false))
                    },
                    onItemEditClick = {
                        navController.navigate(Screen.MedicationDetails.createRoute(it.id, true))
                    }
                )
                MedicationListScreen(callbacks = callbacks)
            }
            composable(Screen.MedicationDetails.route, Screen.MedicationDetails.arguments) {
                val arguments = it.arguments!!
                MedicationDetailsScreen(
                    medicationId = Screen.MedicationDetails.getMedicationId(arguments),
                    initiallyEditable = Screen.MedicationDetails.getEditable(arguments),
                    onBackClick = { navController.navigateUp() }
                )
            }
            composable(Screen.AddMedication.route) {
                AddMedicationScreen(onBackClick = { navController.navigateUp() })
            }
            composable(Screen.MedicationReminders.route) {
                MedicationRemindersScreen(
                    onAddClick = { navController.navigate(Screen.AddMedicationReminder.route) },
                    onItemClick = { /*TODO*/ })
            }
            composable(Screen.AddMedicationReminder.route) {
                EditMedicationReminderScreen(onBackClick = { navController.navigateUp() })
            }
        }
    }
}