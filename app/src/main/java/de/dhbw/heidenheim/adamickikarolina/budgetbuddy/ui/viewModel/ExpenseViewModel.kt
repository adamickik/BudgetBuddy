package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.ExpenseDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingGoal
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExpenseViewModel(private val expenseDao: ExpenseDao) : ViewModel() {
    val expenses: LiveData<List<Expense>> = expenseDao.getAll()

    fun addExpense(title: String, value: String, date: String) {
        val expenseValue = value.toFloatOrNull() ?: return

        //val formattedDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(date) ?: return

        val newExpense = Expense(title, expenseValue, date, 0)

        viewModelScope.launch {
            expenseDao.insert(newExpense)
        }
    }
    fun getExpensesByAssignmentId(assignmentId: Int): LiveData<List<Expense>> {
        return expenseDao.getByAssignmentId(assignmentId)
    }

    fun assignExpenseToSavingsGoal(value: Float,assigment: SavingGoal) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        //Expense mit Abzug aus SparDepot
        val negativeExpense = assigment.sgId?.let {
            Expense(
                eName = assigment.sgName,
                eAmount = value*(-1),
                eDate = currentDate,
                eAssignment = 0
            )
        }
        if (negativeExpense != null) {
            expenseDao.insert(negativeExpense)
        }

        //Expense mit Zuweisung auf neues Sparziel
        val expense = assigment.sgId?.let {
            Expense(
                eName = assigment.sgName,
                eAmount = value,
                eDate = currentDate,
                eAssignment = it
            )
        }
        if (expense != null) {
            expenseDao.insert(expense)
        }
    }
}
class ExpenseViewModelFactory(private val expenseDao: ExpenseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpenseViewModel(expenseDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}