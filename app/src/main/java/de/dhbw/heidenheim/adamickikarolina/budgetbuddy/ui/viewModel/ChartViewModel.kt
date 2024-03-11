package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.DBHandler


class ChartViewModel(private val dbHandler: DBHandler) : ViewModel() {
    // LineChart
    val targetValue = MutableLiveData<Float>()
    val currentValue = MutableLiveData<Float>()
    val points = MutableLiveData<List<Float>>()
    val savingsGoalName = MutableLiveData<String>()

    // PaymentsPieChart
    val slices = MutableLiveData<List<Float>>(listOf(30f, 10f, 60f))
    //val piechartexpenses = MutableLiveData<List<Float>>()
    //val descriptions = MutableLiveData<List<String>>()



    init {
        var goal = dbHandler.readSavingsGoals()
        targetValue.value = goal[1].savingsGoalAmount
        savingsGoalName.value= goal[1].savingsGoalName
        //points.value = listOf(0f, 10f, 25f, 35f, currentValue.value!!)
        val expenses  = dbHandler.readExpenses(1);
        points.value = expenses.map {it.expenseAmount  }
        transformPointsListAscending(points)

        val lastPoint = dbHandler.getSumOfExpenses(1)
        currentValue.value = lastPoint
        //piechartexpenses.value = dbHandler.readExpenses.(1)
        //descriptions.value = expenses.map {it.expenseName  }

    }


    fun transformPointsListAscending(points: MutableLiveData<List<Float>>) {
        // Get the current list value or return if it's null.
        val currentList = points.value?.toMutableList() ?: return

        var sum = 0f
        for (i in currentList.indices) {
            sum += currentList[i].toInt() // Add the current element's value to sum.
            currentList[i] = sum  // Update the current element with the new sum.
        }

        // Post the transformed list back into the MutableLiveData.
        points.postValue(currentList)
    }

    //max. Value of LineChart
    val maxValue: LiveData<Float> = points.map { pointsList ->
        pointsList.map { it.toFloat() }.maxOrNull()?.coerceAtLeast(targetValue.value ?: 0f) ?: targetValue.value ?: 0f
    }





}
class ChartViewModelFactory(private val dbHandler: DBHandler) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChartViewModel(dbHandler) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}