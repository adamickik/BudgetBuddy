package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.category.Category
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.category.CategoryRepository
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.expense.CategorySum
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.expense.ExpenseRepository
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.savingGoal.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.savingGoal.SavingGoalRepository
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val savingGoalRepository: SavingGoalRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    val savingsGoalsCount : LiveData<Int> = savingGoalRepository.getSavingGoalCount()

    val categories: LiveData<List<Category>> = categoryRepository.getAll()

    val sumCategories: LiveData<List<CategorySum>> = expenseRepository.getAllCategoryExpenses().map { list ->
        list.filterNot { it.categoryId == 1 }
    }

    val sumNegative : LiveData<Float> = expenseRepository.getSumNegative()

    fun getAllSavingGoals():LiveData<List<SavingGoal>>  {
        return savingGoalRepository.getAllSavingGoals()
    }

    fun insertAsList(categories: List<Category>) {
        categoryRepository.insertAsList(categories)
    }

    fun getSumOfExpensesByAssignmentID(assignmentId: Int): LiveData<Float> {
        return expenseRepository.getSumByAssignmentId(assignmentId)
    }

    fun getCategoryNameById(categoryId: Int): LiveData<String> {
        return categoryRepository.getNameByCategoryId(categoryId)
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
                cumulativeList.maxOfOrNull { amount ->
                    maxOf(amount, sgGoalAmount)
                } ?: sgGoalAmount
            }
        }
    }
}