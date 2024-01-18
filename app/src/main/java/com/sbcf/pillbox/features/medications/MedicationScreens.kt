package com.sbcf.pillbox.features.medications

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sbcf.pillbox.features.medications.screens.AddMedicationScreen
import com.sbcf.pillbox.features.medications.screens.MedicationDetailsScreen
import com.sbcf.pillbox.features.medications.screens.MedicationListCallbacks
import com.sbcf.pillbox.features.medications.screens.MedicationListScreen
import com.sbcf.pillbox.screens.Screen

sealed class MedicationScreens {
    data object MedicationList : Screen("medications/list")
    data object AddMedication : Screen("medications/add")
    data object MedicationDetails :
        Screen(
            "medications/details/{medicationId}/{editable}",
            listOf(
                navArgument("medicationId") { type = NavType.IntType },
                navArgument("editable") { type = NavType.BoolType })
        ) {
        fun createRoute(medicationId: Int, initiallyEditable: Boolean) =
            "medications/details/$medicationId/$initiallyEditable"

        fun getMedicationId(bundle: Bundle) = bundle.getInt(arguments[0].name)
        fun getEditable(bundle: Bundle) = bundle.getBoolean(arguments[1].name)
    }
}

fun NavGraphBuilder.medications(navController: NavController) {
    composable(MedicationScreens.MedicationList.route) {
        val callbacks = MedicationListCallbacks(
            onAddMedicationClick = {
                navController.navigate(MedicationScreens.AddMedication.route)
            },
            onItemOpenClick = {
                navController.navigate(
                    MedicationScreens.MedicationDetails.createRoute(
                        it.id,
                        false
                    )
                )
            },
            onItemEditClick = {
                navController.navigate(MedicationScreens.MedicationDetails.createRoute(it.id, true))
            }
        )
        MedicationListScreen(callbacks = callbacks)
    }
    composable(
        MedicationScreens.MedicationDetails.route,
        MedicationScreens.MedicationDetails.arguments
    ) {
        val arguments = it.arguments!!
        MedicationDetailsScreen(
            medicationId = MedicationScreens.MedicationDetails.getMedicationId(arguments),
            initiallyEditable = MedicationScreens.MedicationDetails.getEditable(arguments),
            onBackClick = { navController.navigateUp() }
        )
    }
    composable(MedicationScreens.AddMedication.route) {
        AddMedicationScreen(onBackClick = { navController.navigateUp() })
    }
}