package de.dhbw.heidenheim.adamickikarolina.budgetbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.AnalyticsScreen
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.HomeScreen
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.ProfileScreen
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.Screens
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.BottomNavBar
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.Topbar
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

    BudgetBuddyTheme {
        Scaffold(
            topBar = { Topbar() },
            bottomBar = { BottomNavBar(navController = navController)}
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


