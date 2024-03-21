package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfNavItems=listOf(
    NavItem(
        label = "Home",
        icon = Icons.Filled.Home,
        route = Screens.HomeScreen.name
    ),
    NavItem(
        label = "Analytics",
        icon = Icons.Filled.Settings,
        route = Screens.AnalyticsScreen.name
    ),
    NavItem(
        label = "Profile",
        icon = Icons.Filled.AccountCircle,
        route = Screens.ProfileScreen.name
    ),
)