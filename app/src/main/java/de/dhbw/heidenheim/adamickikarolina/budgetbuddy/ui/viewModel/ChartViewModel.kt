package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.category.Category
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.category.CategoryRepository
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.CategorySum
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.ExpenseRepository
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoalRepository
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val savingGoalRepository: SavingGoalRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    private val savingGoals: LiveData<List<SavingGoal>> = savingGoalRepository.getAllSavingGoals()
    private val expenses: LiveData<List<Expense>> = expenseRepository.getAllExpenses()
    private val categories: LiveData<List<Category>> = categoryRepository.getAllCategories()

    // PaymentsPieChart
    //val slices = MutableLiveData<List<Float>>(listOf(30f, 10f, 60f))

    fun insertAsList(categories: List<Category>) {
        categoryRepository.insertAsList(categories)
    }

    fun getSumNegative(): LiveData<Float> {
        return expenseRepository.getSumNegative()
    }

    fun getSumOfExpensesByAssigmentID(assignmentId: Int): LiveData<Float> {
        return expenseRepository.getSumByAssignmentId(assignmentId)
    }

    fun getSumOfExpensesAllCategories():LiveData<List<CategorySum>> {
        return expenseRepository.getAllCategoryExpenses()
    }

    fun getCategoryNameById(categoryId: Int): LiveData<String> {
        return categoryRepository.getNameByCategoryId(categoryId)
    }

    private fun getSumOfExpensesByCategoryID(kId: Int): LiveData<Float> {
        return expenseRepository.getSumByCategoryId(kId)
    }

    fun getAmountsByCategoryId(catId: Int): LiveData<List<Float>> {
        val amountsLiveData = expenseRepository.getAmountsByAssignmentId(catId)

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