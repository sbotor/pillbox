package com.sbcf.pillbox.features.reminderhistory

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.sbcf.pillbox.features.reminderhistory.views.ReminderHistoryEntryScreen
import com.sbcf.pillbox.screens.Screen

class ReminderHistoryScreens {
    data object ReminderHistoryEntry : Screen(
        "medicationReminders/history/{entryId}",
        listOf(navArgument("entryId") { type = NavType.IntType }),
        listOf(navDeepLink {
            uriPattern = "content://history/{entryId}"
            action = ReminderHistoryEntry.DEEP_LINK_ACTION
        })
    ) {
        const val DEEP_LINK_ACTION = "com.sbcf.pillbox.REMINDER_NOTIFICATION"

        fun createRoute(entryId: Int) = "medicationReminders/history/$entryId"
        fun createDeepLinkPattern(entryId: Int): Uri = Uri.parse("content://history/$entryId")
        fun getEntryId(bundle: Bundle) = bundle.getInt(arguments[0].name)
    }
}

fun NavGraphBuilder.reminderHistory(navController: NavController) {
    composable(
        ReminderHistoryScreens.ReminderHistoryEntry.route,
        ReminderHistoryScreens.ReminderHistoryEntry.arguments,
        ReminderHistoryScreens.ReminderHistoryEntry.deepLinks
    ) {
        val entryId = ReminderHistoryScreens.ReminderHistoryEntry.getEntryId(it.arguments!!)
        ReminderHistoryEntryScreen(entryId = entryId, onBackClick = { navController.navigateUp() })
    }
}