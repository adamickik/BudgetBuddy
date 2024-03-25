package de.dhbw.heidenheim.adamickikarolina.budgetbuddy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData.InitialExpenseData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData.InitialSavingGoalData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData.InitialTippData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tipp.Tipp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.BudgetBuddyApp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.TippsViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val expenseViewModel: ExpenseViewModel by viewModels()
    private val savingsGoalViewModel: SavingsGoalViewModel by viewModels()
    private val tippsViewModel: TippsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fillDatabaseInitially(InitialTippData.initialTipps, InitialExpenseData.initialExpenses, InitialSavingGoalData.initialSavingGoals)

        setContent {
            BudgetBuddyApp()
        }
    }

    private fun fillDatabaseInitially(tipps: List<Tipp>, expenses: List<Expense>, savingGoals: List<SavingGoal>) {
        if (tippsViewModel.getTippCount()==0) {
            tippsViewModel.insertAsList(tipps)
            val savedSavingGoalsIds = savingsGoalViewModel.insertAsListAndGetIds(savingGoals)
            Log.d("MainActivity", "Gespeicherte SavingGoal IDs: $savedSavingGoalsIds")
            val updatedExpenses = expenses.map { expense ->
                expense.copy(eAssignment = savedSavingGoalsIds[expense.eAssignment].toInt())
            }
            expenseViewModel.insertAsList(updatedExpenses)
        }
    }

    override fun onPause() {
        super.onPause()
        val intent = Intent(this, BudgetBuddyWidget::class.java).apply {
            action = "de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ACTION_UPDATE_WIDGET"
        }
        this.sendBroadcast(intent)
    }
}
