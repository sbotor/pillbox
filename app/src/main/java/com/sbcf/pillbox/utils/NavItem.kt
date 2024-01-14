package com.sbcf.pillbox.utils

import com.sbcf.pillbox.R
import com.sbcf.pillbox.screens.Screen


sealed class NavItem(
    val title: Int,
    val icon: Int,
    val route: String
) {
    data object Home: NavItem(
        R.string.home_page,
        R.drawable.home_24,
        Screen.Home.route
        )

    data object  Medication: NavItem(
        R.string.medication,
        R.drawable.medication_24,
        Screen.Medication.route
        )
}