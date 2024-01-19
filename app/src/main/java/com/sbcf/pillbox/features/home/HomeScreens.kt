package com.sbcf.pillbox.features.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sbcf.pillbox.features.home.screens.HomeScreen
import com.sbcf.pillbox.features.medicationreminders.MedicationReminderScreens
import com.sbcf.pillbox.features.reminderhistory.ReminderHistoryScreens
import com.sbcf.pillbox.screens.Screen

sealed class HomeScreens {
    data object Home : Screen("home")
}

fun NavGraphBuilder.home(navController: NavController) {
    composable(HomeScreens.Home.route) {
        HomeScreen(
            onNextReminderClick = {
                navController.navigate(MedicationReminderScreens.MedicationReminders.route)
            },
            onUnreadReminderClick = {
                navController.navigate(ReminderHistoryScreens.ReminderHistoryEntry.createRoute(it))
            },
            onHistoryClick = {
                navController.navigate(ReminderHistoryScreens.ReminderHistoryList.route)
            }
        )
    }
}