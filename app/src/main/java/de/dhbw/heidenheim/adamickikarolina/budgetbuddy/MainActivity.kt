package de.dhbw.heidenheim.adamickikarolina.budgetbuddy

import android.content.Intent
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData.InitialExpenseData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData.InitialSavingGoalData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData.InitialTippData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.AppDatabase
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.ExpenseDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingDepot
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingDepotDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingGoalDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.Tipp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.TippDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData.InitialSavingDepotData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.BudgetBuddyApp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ChartViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ChartViewModelFactory
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseViewModelFactory
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingDepotViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsDepotViewModelFactory
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalViewModelFactory
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.TippsViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.TippsViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var savingsGoalViewModel: SavingsGoalViewModel
    private lateinit var chartViewModel: ChartViewModel
    private lateinit var tippsViewModel: TippsViewModel
    private lateinit var savingDepotViewModel: SavingDepotViewModel

    private lateinit var expenseDao: ExpenseDao
    private lateinit var tippDao: TippDao
    private lateinit var savingGoalDao: SavingGoalDao
    private lateinit var savingDepotDao: SavingDepotDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create database via builder pattern
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "budgetbuddyDB")
            .allowMainThreadQueries()
            .build()

        // Get daos from database
        expenseDao = db.getExpenseDao()
        tippDao = db.getTippDao()
        savingGoalDao = db.getSavingGoalDao()
        savingDepotDao = db.getSavingDepotDao()

        fillDatabaseInitially(InitialTippData.initialTipps, InitialExpenseData.initialExpenses, InitialSavingGoalData.initialSavingGoals, InitialSavingDepotData.initialSavingDepot)

        val expenseFactory = ExpenseViewModelFactory(expenseDao)
        val savingsGoalFactory = SavingsGoalViewModelFactory(savingGoalDao)
        val savingDepotFactory = SavingsDepotViewModelFactory(savingDepotDao)
        val chartFactory = ChartViewModelFactory(expenseDao, savingGoalDao)
        val tippFactory = TippsViewModelFactory(tippDao)

        expenseViewModel = ViewModelProvider(this, expenseFactory).get(ExpenseViewModel::class.java)
        savingsGoalViewModel = ViewModelProvider(this, savingsGoalFactory).get(SavingsGoalViewModel::class.java)
        chartViewModel = ViewModelProvider(this, chartFactory).get(ChartViewModel::class.java)
        tippsViewModel = ViewModelProvider(this, tippFactory).get(TippsViewModel::class.java)
        savingDepotViewModel = ViewModelProvider(this, savingDepotFactory).get(SavingDepotViewModel::class.java)

        setContent {
            BudgetBuddyApp(expenseViewModel, savingsGoalViewModel, chartViewModel, tippsViewModel, savingDepotViewModel)
        }
    }

    private fun fillDatabaseInitially(tipps: List<Tipp>, expenses: List<Expense>, savingGoals: List<SavingGoal>, savingDepot: List<SavingDepot>) {
        // Fill list initially if tables are empty
        // TODO: better logic for initially filling db
        if (tippDao.getCount()==0) {
            tippDao.insertAsList(tipps)
            expenseDao.insertAsList(expenses)
            savingGoalDao.insertAsList(savingGoals)
            savingDepotDao.insertAsList(savingDepot)
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
