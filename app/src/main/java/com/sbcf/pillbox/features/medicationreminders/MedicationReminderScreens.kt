package com.sbcf.pillbox.features.medicationreminders

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sbcf.pillbox.features.medicationreminders.screens.EditMedicationReminderScreen
import com.sbcf.pillbox.features.medicationreminders.screens.MedicationRemindersScreen
import com.sbcf.pillbox.screens.Screen

sealed class MedicationReminderScreens {
    data object MedicationReminders : Screen("medicationReminders")
    data object AddMedicationReminder : Screen("medicationReminders/add")
    data object EditMedicationReminder : Screen(
        "medicationReminders/edit/{reminderId}", listOf(
            navArgument("reminderId") { type = NavType.IntType })
    ) {
        fun createRoute(reminderId: Int) = "medicationReminders/edit/$reminderId"
        fun getReminderId(bundle: Bundle) = bundle.getInt(arguments[0].name)
    }
}

fun NavGraphBuilder.medicationReminders(navController: NavController) {
    composable(MedicationReminderScreens.MedicationReminders.route) {
        MedicationRemindersScreen(
            onAddClick = { navController.navigate(MedicationReminderScreens.AddMedicationReminder.route) },
            onItemClick = {
                navController.navigate(
                    MedicationReminderScreens.EditMedicationReminder.createRoute(
                        it.id
                    )
                )
            })
    }

    composable(MedicationReminderScreens.AddMedicationReminder.route) {
        EditMedicationReminderScreen(
            onBackClick = { navController.navigateUp() })
    }

    composable(
        MedicationReminderScreens.EditMedicationReminder.route,
        MedicationReminderScreens.EditMedicationReminder.arguments
    ) {
        val arguments = it.arguments!!
        EditMedicationReminderScreen(
            onBackClick = { navController.navigateUp() },
            reminderId = MedicationReminderScreens.EditMedicationReminder.getReminderId(arguments)
        )
    }
}