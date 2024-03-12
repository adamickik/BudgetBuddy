package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.ExpenseDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingGoalDao
import kotlinx.coroutines.launch


class ChartViewModel(
    private val expenseDao: ExpenseDao,
    private val savingGoalDao: SavingGoalDao,
) : ViewModel() {
    // LineChart
    val targetValue = MutableLiveData<Float>()
    val currentValue = MutableLiveData<Float>()
    val points = MutableLiveData<List<Float>>()
    //val savingsGoalName = MutableLiveData<String>()

    // PaymentsPieChart
    val slices = MutableLiveData<List<Float>>(listOf(30f, 10f, 60f))
    //val piechartexpenses = MutableLiveData<List<Float>>()
    //val descriptions = MutableLiveData<List<String>>()
    private val savingGoals = savingGoalDao.getAll()

    // Transformations.map wird verwendet, um von SavingGoals die Namen zu extrahieren
    val savingsGoalName: LiveData<String> = savingGoals.map { goals ->
        if(goals.isNotEmpty()) goals[0].sgName else "Default"}

    init {
        loadInitialData()
    }

    // TODO change so that update does not only happen on init
    private fun loadInitialData(){
        viewModelScope.launch {
            savingGoalDao.getAll().value?.let { goals ->
                if (goals.isNotEmpty()) {
                    val goal = goals[0]
                    targetValue.value = goal.sgGoalAmount
                    //savingsGoalName.value = goal.sgName
                }
            }

            expenseDao.getAll().value?.let { expenses ->
                points.value = expenses.map { it.eAmount.toFloat() }
                transformPointsListAscending()
            }
        }
    }

    private fun transformPointsListAscending() {
        points.value?.let { list ->
            var sum = 0f
            val transformedList = list.map { amount ->
                sum += amount
                sum
            }
            points.postValue(transformedList)
        }
    }


    //max. Value of LineChart
    val maxValue: LiveData<Float> = points.map { pointsList ->
        pointsList.map { it.toFloat() }.maxOrNull()?.coerceAtLeast(targetValue.value ?: 0f) ?: targetValue.value ?: 0f
    }
}
class ChartViewModelFactory(
    private val expenseDao: ExpenseDao,
    private val savingGoalDao: SavingGoalDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChartViewModel(expenseDao, savingGoalDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}