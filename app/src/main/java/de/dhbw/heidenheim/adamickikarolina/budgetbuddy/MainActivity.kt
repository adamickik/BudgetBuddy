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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.AnalyticsScreen
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.HomeScreen
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.ProfileScreen
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.Screens
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.BottomNavBar
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.SavingTipsDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.Topbar
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.theme.BudgetBuddyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BudgetBuddyApp()
        }
    }
}

@Preview
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
                composable(route=Screens.HomeScreen.name){
                    HomeScreen()
                }
                composable(route=Screens.AnalyticsScreen.name){
                    AnalyticsScreen()
                }
                composable(route=Screens.ProfileScreen.name){
                    ProfileScreen()
                }
            }
        }
    }
}


