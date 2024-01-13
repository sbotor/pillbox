package com.sbcf.pillbox.screens

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
                onClick = {
                    navController.navigate(item.route) {

                        navController.graph.startDestinationRoute?.let { screenRoute ->
                            popUpTo(screenRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon( painterResource(id = item.icon), contentDescription = null) },
                label = { Text(stringResource(id = item.title)) }
            )
        }
        /*items.forEach {item ->
            AddItem(
                screen = item,
                navController = navController
            )
        }*/
    }
    
}

/*
@Composable
fun RowScope.AddItem(
    screen: NavItem,
    navController: NavController
)
{
    NavigationBarItem(
        selected = true,
        label = {
            Text(text = screen.title)
        },
        onClick = { navController.navigate(Screen.MedicationList.route) },
        icon = { Icon(painter = painterResource(id = screen.icon), contentDescription = screen.title ) })
}*/