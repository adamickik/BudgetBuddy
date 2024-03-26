package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.screens.AnalyticsScreen
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.screens.HomeScreen
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.screens.ProfileScreen
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.screens.Screens
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.ConfirmDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.SavingTipsDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.Topbar
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.theme.BudgetBuddyTheme

@Composable
fun BudgetBuddyApp() {
    val navController = rememberNavController()

    var showDialog by remember{ mutableStateOf(false) }

    BudgetBuddyTheme {
        if (showDialog) {
            SavingTipsDialog(onDismissRequest = { showDialog = false })
        }

        Scaffold(
            topBar = { Topbar(onDialogButtonClick = {showDialog=true}) },
            bottomBar = { BottomNavBar(navController = navController) }
        ) {paddingValues ->
            NavHost(
                navController= navController,
                startDestination= Screens.HomeScreen.name,
                modifier = Modifier.padding(paddingValues)
            ){
                composable(route= Screens.HomeScreen.name){
                    HomeScreen()
                }
                composable(route= Screens.AnalyticsScreen.name){
                    AnalyticsScreen()
                }
                composable(route= Screens.ProfileScreen.name){
                    ProfileScreen()
                }
            }
        }
    }
}
