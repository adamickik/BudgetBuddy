package de.dhbw.heidenheim.adamickikarolina.budgetbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.AnalyticsScreen
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.DBHandler
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.HomeScreen
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.ProfileScreen
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.Screens
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.BottomNavBar
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.SavingTipsDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.Topbar
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.theme.BudgetBuddyTheme
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ChartViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ChartViewModelFactory
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseViewModelFactory
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var savingsGoalViewModel: SavingsGoalViewModel
    private lateinit var chartViewModel: ChartViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbHandler = DBHandler(this)
        val expenseFactory = ExpenseViewModelFactory(dbHandler)
        val savingsGoalFactory = SavingsGoalViewModelFactory(dbHandler)
        val chartFactory = ChartViewModelFactory(dbHandler)
        expenseViewModel = ViewModelProvider(this, expenseFactory).get(ExpenseViewModel::class.java)
        savingsGoalViewModel = ViewModelProvider(this, savingsGoalFactory).get(SavingsGoalViewModel::class.java)
        chartViewModel = ViewModelProvider(this, chartFactory).get(ChartViewModel::class.java)
        setContent {
            BudgetBuddyApp(expenseViewModel, savingsGoalViewModel, chartViewModel, dbHandler)
        }
    }
}

@Composable
fun BudgetBuddyApp(expenseViewModel: ExpenseViewModel, savingsGoalViewModel: SavingsGoalViewModel, chartViewModel: ChartViewModel, dbHandler: DBHandler) {
    val navController = rememberNavController()
    var showDialog by remember{ mutableStateOf(false) }

    BudgetBuddyTheme {
        if (showDialog) {
            SavingTipsDialog(onDismissRequest = { showDialog = false }, dbHandler.readRandomTipp()[0])
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
                composable(route=Screens.HomeScreen.name){
                    HomeScreen(expenseViewModel,savingsGoalViewModel)
                }
                composable(route=Screens.AnalyticsScreen.name){
                    AnalyticsScreen(chartViewModel)
                }
                composable(route=Screens.ProfileScreen.name){
                    ProfileScreen()
                }
            }
        }
    }
}


