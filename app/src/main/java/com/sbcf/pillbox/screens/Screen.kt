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
            "medications/details/{medicationId}",
            listOf(navArgument("medicationId") { type = NavType.IntType })
        ) {
        fun createRoute(medicationId: Int) = "medications/details/$medicationId"
        fun getMedicationId(bundle: Bundle): Int {
            return bundle.getInt(arguments[0].name)
        }
    }
    data object Home : Screen("home")
}