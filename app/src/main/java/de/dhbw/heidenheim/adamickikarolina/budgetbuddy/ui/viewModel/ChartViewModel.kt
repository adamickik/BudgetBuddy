package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map


class ChartViewModel : ViewModel() {
    // LineChart
    val targetValue = MutableLiveData<Float>()
    val currentValue = MutableLiveData<Float>()
    val points = MutableLiveData<List<Float>>()
    val savingsGoalName = MutableLiveData<String>()

    // PaymentsPieChart
    val slices = MutableLiveData<List<Float>>(listOf(30f, 10f, 60f))

    init {
        targetValue.value = 100f
        currentValue.value = 50f
        savingsGoalName.value="AUTO"
        points.value = listOf(0f, 10f, 25f, 35f, currentValue.value!!)
    }

    // max. Value of LineChart
    val maxValue: LiveData<Float> = points.map{ pointsList ->
        pointsList.maxOrNull()?.coerceAtLeast(targetValue.value ?: 0f) ?: targetValue.value ?: 0f
    }
}