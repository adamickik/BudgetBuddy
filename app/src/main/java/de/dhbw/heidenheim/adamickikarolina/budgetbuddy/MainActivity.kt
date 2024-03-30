package de.dhbw.heidenheim.adamickikarolina.budgetbuddy

import InitialBootScreen
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dagger.hilt.android.AndroidEntryPoint
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.category.Category
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.expense.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.initialData.InitialCategoryData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.initialData.InitialExpenseData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.initialData.InitialSavingGoalData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.initialData.InitialTippData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.savingGoal.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.tip.Tip
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.BudgetBuddyApp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.viewModel.AnalyticsViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.viewModel.ExpenseViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.viewModel.SavingTipsViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.viewModel.SavingsGoalViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val expenseViewModel: ExpenseViewModel by viewModels()
    private val savingsGoalViewModel: SavingsGoalViewModel by viewModels()
    private val analyticsViewModel: AnalyticsViewModel by viewModels()
    private val tippsViewModel: SavingTipsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fillDatabaseInitially(InitialTippData.initialTips, InitialExpenseData.initialExpenses, InitialSavingGoalData.initialSavingGoals, InitialCategoryData.initialCategories )

        setContent {
            var setupComplete by remember { mutableStateOf(!isFirstAppLaunch()) }

            if (!setupComplete) {
                InitialBootScreen(onSetupComplete = {
                    setAppLaunched()
                    setupComplete = true
                })
            } else {
                BudgetBuddyApp()
            }
        }
    }

    private fun fillDatabaseInitially(tips: List<Tip>, expenses: List<Expense>, savingGoals: List<SavingGoal>, categories: List<Category>) {
        if (tippsViewModel.getTipCount()==0) {
            tippsViewModel.insertAsList(tips)
            savingsGoalViewModel.insertAsListAndGetIds(savingGoals)
            analyticsViewModel.insertAsList(categories)
            expenseViewModel.insertAsList(expenses)
        }
    }

    override fun onPause() {
        super.onPause()
        val intent = Intent(this, BudgetBuddyWidget::class.java).apply {
            action = "de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ACTION_UPDATE_WIDGET"
        }
        this.sendBroadcast(intent)
    }
    private fun isFirstAppLaunch(): Boolean {
        val sharedPref = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        return sharedPref.getBoolean("isFirstLaunch", true)
    }

    private fun setAppLaunched() {
        val sharedPref = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("isFirstLaunch", false)
            apply()
        }
    }
}
