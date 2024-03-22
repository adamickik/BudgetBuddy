package de.dhbw.heidenheim.adamickikarolina.budgetbuddy

import android.content.Intent
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData.InitialExpenseData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData.InitialSavingGoalData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData.InitialTippData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingDepot.SavingDepot
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tipp.Tipp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData.InitialSavingDepotData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.BudgetBuddyApp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingDepotViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.TippsViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val expenseViewModel: ExpenseViewModel by viewModels()
    private val savingsGoalViewModel: SavingsGoalViewModel by viewModels()
    private val tippsViewModel: TippsViewModel by viewModels()
    private val savingDepotViewModel: SavingDepotViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fillDatabaseInitially(InitialTippData.initialTipps, InitialExpenseData.initialExpenses, InitialSavingGoalData.initialSavingGoals, InitialSavingDepotData.initialSavingDepot)

        setContent {
            BudgetBuddyApp()
        }
    }

    private fun fillDatabaseInitially(tipps: List<Tipp>, expenses: List<Expense>, savingGoals: List<SavingGoal>, savingDepot: List<SavingDepot>) {
        // Fill list initially if tables are empty
        if (tippsViewModel.getTippCount()==0) {
            tippsViewModel.insertAsList(tipps)
            expenseViewModel.insertAsList(expenses)
            savingsGoalViewModel.insertAsList(savingGoals)
            savingDepotViewModel.insertAsList(savingDepot)
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
