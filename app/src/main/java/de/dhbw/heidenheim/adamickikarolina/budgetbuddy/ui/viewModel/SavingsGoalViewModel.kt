package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.DBHandler
import kotlinx.coroutines.launch

class SavingsGoalViewModel(private val dbHandler: DBHandler) : ViewModel() {

    private val _savingsGoals = MutableLiveData<List<SavingsGoalModel>>()
    val savingsGoals: LiveData<List<SavingsGoalModel>> = _savingsGoals

    init {
        loadSavingsGoals()
    }

    private fun loadSavingsGoals() {
        viewModelScope.launch {
            val savingsGoalList = dbHandler.readSavingsGoals()
            _savingsGoals.postValue(savingsGoalList ?: emptyList())
        }
    }

    fun addSavingsGoal(name: String) {
        viewModelScope.launch {
            dbHandler.addNewSavingGoal(name)
            loadSavingsGoals() // Refresh the list
        }
    }
}

class SavingsGoalViewModelFactory(private val dbHandler: DBHandler) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavingsGoalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SavingsGoalViewModel(dbHandler) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}