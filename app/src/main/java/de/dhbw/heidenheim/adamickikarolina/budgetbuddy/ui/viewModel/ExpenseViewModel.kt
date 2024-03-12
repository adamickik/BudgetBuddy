package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.ExpenseDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.DBHandler
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class ExpenseViewModel(private val expenseDao: ExpenseDao) : ViewModel() {
    val expenses: LiveData<List<Expense>> = expenseDao.getAll()

    fun addExpense(title: String, value: String, date: String) {
        val expenseValue = value.toFloatOrNull() ?: return
        if(expenseValue== null){
            Log.e("ExpenseViewModel", "Fehler bei der Konvertierung von $value zu Float")
            return
        }

        //val formattedDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(date) ?: return

        val newExpense = Expense(title, expenseValue, date, 0)

        viewModelScope.launch {
            Log.d("ExpenseViewModel", "Füge neue Ausgabe hinzu: $title")
            expenseDao.insert(newExpense)
            Log.d("ExpenseViewModel", "Neue Ausgabe hinzugefügt: $title")
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