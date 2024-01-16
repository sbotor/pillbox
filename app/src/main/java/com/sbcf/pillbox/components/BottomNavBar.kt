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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavItem.allItems.forEach { item ->
            val label = stringResource(id = item.titleId)
            NavigationBarItem(
                selected = currentRoute?.startsWith(item.route) ?: false,
                onClick = { if (currentRoute != item.route) navController.navigate(item.route) },
                icon = { Icon(painterResource(id = item.iconId), contentDescription = label) },
                label = { Text(label) }
            )
        }
    }
}

