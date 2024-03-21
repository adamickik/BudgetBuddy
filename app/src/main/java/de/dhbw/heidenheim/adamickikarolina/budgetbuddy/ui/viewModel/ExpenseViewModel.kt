package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.ExpenseDao
import kotlinx.coroutines.launch

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