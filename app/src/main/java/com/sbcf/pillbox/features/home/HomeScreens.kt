package com.sbcf.pillbox.features.home

import android.os.Bundle
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sbcf.pillbox.features.home.screens.HomeScreen
import com.sbcf.pillbox.screens.Screen

sealed class HomeScreens {
    data object Home : Screen("home")
}

fun NavGraphBuilder.home() {
    composable(HomeScreens.Home.route) {
        HomeScreen()
    }
}