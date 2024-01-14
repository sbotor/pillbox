package com.sbcf.pillbox.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sbcf.pillbox.features.medications.screens.AddMedicationScreen
import com.sbcf.pillbox.features.medications.screens.HomeScreen
import com.sbcf.pillbox.features.medications.screens.MedicationDetailsScreen
import com.sbcf.pillbox.features.medications.screens.MedicationListScreen
import com.sbcf.pillbox.features.medications.screens.MedicationScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PillBoxApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        }
    )
    {
        NavHost(navController = navController, Screen.Home.route) {
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
            composable(Screen.Home.route){
                HomeScreen()
            }
            composable(Screen.Medication.route){
                MedicationScreen(onMedicationClick = { navController.navigate(Screen.MedicationList.route) })
            }
        }
    }


}