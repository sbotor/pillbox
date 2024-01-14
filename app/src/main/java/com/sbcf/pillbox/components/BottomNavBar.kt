package com.sbcf.pillbox.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sbcf.pillbox.utils.NavItem

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        NavItem.Home,
        NavItem.Medication
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { navController.navigate(item.route) },
                icon = { Icon(painterResource(id = item.icon), contentDescription = null) },
                label = { Text(stringResource(id = item.title)) }
            )
        }
    }
}

