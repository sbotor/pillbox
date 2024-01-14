package com.sbcf.pillbox.screens

import android.os.Bundle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String, val arguments: List<NamedNavArgument> = emptyList()) {
    data object MedicationList : Screen("medications")

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

        fun getMedicationId(bundle: Bundle): Int {
            return bundle.getInt(arguments[0].name)
        }

        fun getEditable(bundle: Bundle): Boolean {
            return bundle.getBoolean(arguments[1].name)
        }
    }

    data object Home : Screen("home")
    data object Medication : Screen("medication") //TODO: better route?
}