package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingGoalDao
import kotlinx.coroutines.launch

class SavingsGoalViewModel(private val savingGoalDao: SavingGoalDao) : ViewModel() {

    val savingsGoals: LiveData<List<SavingGoal>> = savingGoalDao.getAll()

    fun addSavingsGoal(title: String, value: String, date: String) {
        val expenseValue = value.toFloatOrNull() ?: return
        val newSavingGoal = SavingGoal(title, expenseValue, date)

        savingGoalDao.insert(newSavingGoal)
    }
}

class SavingsGoalViewModelFactory(private val savingGoalDao: SavingGoalDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavingsGoalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SavingsGoalViewModel(savingGoalDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}