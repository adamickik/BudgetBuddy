package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.AnalyticsScreen
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.HomeScreen
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.ProfileScreen
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.Screens
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.theme.BudgetBuddyTheme
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ChartViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.TippsViewModel

@Composable
fun BudgetBuddyApp(expenseViewModel: ExpenseViewModel, savingsGoalViewModel: SavingsGoalViewModel, chartViewModel: ChartViewModel, tippsViewModel: TippsViewModel) {
    val navController = rememberNavController()
    var showDialog by remember{ mutableStateOf(false) }

    BudgetBuddyTheme {
        if (showDialog) {
            SavingTipsDialog(onDismissRequest = { showDialog = false }, tippsViewModel)
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
                    HomeScreen(expenseViewModel,savingsGoalViewModel)
                }
                composable(route= Screens.AnalyticsScreen.name){
                    AnalyticsScreen(chartViewModel)
                }
                composable(route= Screens.ProfileScreen.name){
                    ProfileScreen(savingsGoalViewModel)
                }
            }
        }
    }
}


