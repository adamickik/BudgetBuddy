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
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.category.Category
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData.InitialCategoryData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData.InitialExpenseData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData.InitialSavingGoalData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData.InitialTippData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tip.Tip
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.BudgetBuddyApp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ChartViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.TipsViewModel


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val expenseViewModel: ExpenseViewModel by viewModels()
    private val savingsGoalViewModel: SavingsGoalViewModel by viewModels()
    private val chartViewModel: ChartViewModel by viewModels()
    private val tippsViewModel: TipsViewModel by viewModels()

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
            chartViewModel.insertAsList(categories)
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
