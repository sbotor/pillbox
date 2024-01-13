package com.sbcf.pillbox.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sbcf.pillbox.features.medications.screens.MedicationDetailsScreen
import com.sbcf.pillbox.features.medications.screens.AddMedicationScreen
import com.sbcf.pillbox.features.medications.screens.MedicationListScreen

@Composable
fun PillBoxApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, Screen.MedicationList.route) {
        composable(Screen.MedicationList.route) {
            MedicationListScreen(
                onAddMedicationClick = { navController.navigate(Screen.AddMedication.route) },
                onItemClick = { navController.navigate(Screen.MedicationDetails.createRoute(it.id)) })
        }
        composable(Screen.MedicationDetails.route, Screen.MedicationDetails.arguments) {
            MedicationDetailsScreen(
                Screen.MedicationDetails.getMedicationId(it.arguments!!),
                onBackClick = { navController.navigateUp() })
        }
        composable(Screen.AddMedication.route) {
            AddMedicationScreen(onBackClick = { navController.navigateUp() })
        }
    }
}