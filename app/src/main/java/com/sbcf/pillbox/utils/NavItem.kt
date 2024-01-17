package com.sbcf.pillbox.utils

import com.sbcf.pillbox.R
import com.sbcf.pillbox.features.home.HomeScreens
import com.sbcf.pillbox.features.medicationreminders.MedicationReminderScreens
import com.sbcf.pillbox.features.medications.MedicationScreens

sealed class NavItem(
    val titleId: Int,
    val iconId: Int,
    val route: String
) {
    data object Home : NavItem(
        R.string.home_page_nav,
        R.drawable.home_24,
        HomeScreens.Home.route
    )

    data object Medication : NavItem(
        R.string.medication_nav,
        R.drawable.medication_24,
        MedicationScreens.MedicationList.route
    )

    data object MedicationReminders : NavItem(
        R.string.medication_reminders,
        R.drawable.alarm_24,
        MedicationReminderScreens.MedicationReminders.route
    )

    companion object {
        val allItems = listOf(
            Medication,
            Home,
            MedicationReminders
        )
    }
}