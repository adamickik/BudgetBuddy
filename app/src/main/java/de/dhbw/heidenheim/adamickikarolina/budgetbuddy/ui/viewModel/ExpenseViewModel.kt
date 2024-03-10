package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.DBHandler
import kotlinx.coroutines.launch

class ExpenseViewModel(private val dbHandler: DBHandler) : ViewModel() {

    private val _expenses = MutableLiveData<List<ExpenseModel>>()
    val expenses: LiveData<List<ExpenseModel>> = _expenses

    init {
        loadExpenses()
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            val expenseList = dbHandler.readExpenses()
            _expenses.postValue(expenseList ?: emptyList())
        }
    }

    fun addExpense(name: String, amount: Int, assignment: Int) {
        viewModelScope.launch {
            dbHandler.addNewExpense(name, amount, assignment)
            loadExpenses() // Refresh the list
        }
    }
}
class ExpenseViewModelFactory(private val dbHandler: DBHandler) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpenseViewModel(dbHandler) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}