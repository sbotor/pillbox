package com.sbcf.pillbox.utils

import com.sbcf.pillbox.R
import com.sbcf.pillbox.screens.Screen


sealed class NavItem(
    val titleId: Int,
    val iconId: Int,
    val route: String
) {
    data object Home : NavItem(
        R.string.home_page_nav,
        R.drawable.home_24,
        Screen.Home.route
    )

    data object Medication : NavItem(
        R.string.medication_nav,
        R.drawable.medication_24,
        Screen.MedicationList.route
    )

    data object MedicationReminders : NavItem(
        R.string.medication_reminders,
        R.drawable.alarm_24,
        Screen.MedicationReminders.route
    )

    companion object {
        val allItems = listOf(
            Medication,
            Home,
            MedicationReminders
        )
    }
}