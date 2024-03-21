package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.Expense
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

    // PaymentsPieChart
    val slices = MutableLiveData<List<Float>>(listOf(30f, 10f, 60f))
    private val savingGoals = savingGoalDao.getAll()

    private val expenses: LiveData<List<Expense>> = expenseDao.getAll()
    val points: LiveData<List<Float>> = expenses.map { expensesList ->
        expensesList.map { it.eAmount }.also {
            transformPointsListAscending(it)
        }
    }
    private fun transformPointsListAscending(points: List<Float>): List<Float> {
        return points.sorted()
    }

    val savingsGoalName: LiveData<String> = savingGoals.map { goals ->
        if(goals.isNotEmpty()) goals[0].sgName else "Default"
    }

    /*
    init {
        loadInitialData()
    }

    // TODO change so that update does not only happen on init
    private fun loadInitialData() {
        viewModelScope.launch {
            savingGoalDao.getAll().value?.let { goals ->
                if (goals.isNotEmpty()) {
                    val goal = goals[0]
                    targetValue.value = goal.sgGoalAmount
                    // Logge das Ziel
                    Log.d("loadInitialData", "Erstes Sparziel geladen: ${goal.sgName}, Zielbetrag: ${goal.sgGoalAmount}")
                } else {
                    Log.d("loadInitialData", "Keine Sparziele gefunden.")
                }
            }

            expenseDao.getAll().value?.let { expenses ->
                if (expenses.isNotEmpty()) {
                    // Logge die Ausgaben
                    Log.d("loadInitialData", "Ausgaben geladen: ${expenses.joinToString { it.eAmount.toString() }}")
                } else {
                    Log.d("loadInitialData", "Keine Ausgaben gefunden.")
                }
                points.value = expenses.map { it.eAmount }
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
*/

    //max. Value of LineChart
    val maxValue: LiveData<Float> = points.map { pointsList ->

        val maxPoint = pointsList.maxOrNull() ?: 0f
        maxPoint.coerceAtLeast(targetValue.value ?: 0f)
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