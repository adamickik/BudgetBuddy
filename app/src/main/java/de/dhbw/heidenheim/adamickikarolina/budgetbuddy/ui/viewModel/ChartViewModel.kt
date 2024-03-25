package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.ExpenseRepository
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoalRepository
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val savingGoalRepository: SavingGoalRepository
) : ViewModel() {

    private val savingGoals: LiveData<List<SavingGoal>> = savingGoalRepository.getAllSavingGoals()
    private val expenses: LiveData<List<Expense>> = expenseRepository.getAllExpenses()
    val categoryExpensesSummary = expenseRepository.getCategoryExpensesSummary()


    // PaymentsPieChart
    val slices = MutableLiveData<List<Float>>(listOf(30f, 10f, 60f))

    fun getSumOfExpensesByAssigmentID(assignmentId: Int): LiveData<Float> {
        return expenseRepository.getSumByAssignmentId(assignmentId)
    }

    /*
    fun getSumsForAllAssignments(): LiveData<List<Float>> {
        val assignmentIds = expenseRepository.getAllAssignmentIds()

        return assignmentIds.map { assignmentIdList ->
            val sumList = mutableListOf<Float>()
            var sum = 0f

            assignmentIdList.forEach { id ->
                sum = getSumOfExpensesByAssigmentID(id).toString().toFloat()
                sumList.add(sum)
            }
            sumList
        }
    }*/

    fun getCumulativeAmountsByAssignmentId(assignmentId: Int): LiveData<List<Float>> {
        val amountsLiveData = expenseRepository.getAmountsByAssignmentId(assignmentId)

        return amountsLiveData.map { amountsList ->
            val cumulativeList = mutableListOf<Float>()
            var sum = 0f
            cumulativeList.add(0f)
            amountsList.forEach { amount ->
                sum += amount
                cumulativeList.add(sum)
            }
            cumulativeList
        }
    }

    private fun getSgGoalAmountById(sgId: Int): LiveData<Float> {
        val savingGoalLiveData = savingGoalRepository.getSavingGoalById(sgId)
        return savingGoalLiveData.map { savingGoal ->
            savingGoal.sgGoalAmount
        }
    }

    fun getMaxCumulativeAmountByAssignmentId(assignmentId: Int): LiveData<Float> {
        val cumulativeAmountsLiveData = getCumulativeAmountsByAssignmentId(assignmentId)
        val sgGoalAmountLiveData = getSgGoalAmountById(assignmentId)

        return sgGoalAmountLiveData.switchMap { sgGoalAmount ->
            cumulativeAmountsLiveData.map { cumulativeList ->
                val lastAmount = if (cumulativeList.isNotEmpty()) cumulativeList.last() else 0f
                maxOf(lastAmount, sgGoalAmount)
            }
        }
    }
}