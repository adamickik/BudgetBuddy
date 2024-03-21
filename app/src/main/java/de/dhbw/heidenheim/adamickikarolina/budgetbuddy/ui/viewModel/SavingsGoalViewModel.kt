package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoalDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoalRepository

@HiltViewModel
class SavingsGoalViewModel(
    private val savingGoalRepository: SavingGoalRepository
) : ViewModel() {

    val savingsGoals: LiveData<List<SavingGoal>> = savingGoalRepository.getAllSavingGoals()

    fun addSavingsGoal(title: String, value: String, date: String) {
        val expenseValue = value.toFloatOrNull() ?: return
        val newSavingGoal = SavingGoal(title, expenseValue, date)

        savingGoalRepository.insert(newSavingGoal)
    }
}