package com.sbcf.pillbox.screens

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

abstract class Screen(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
    val deepLinks: List<NavDeepLink> = emptyList()
)