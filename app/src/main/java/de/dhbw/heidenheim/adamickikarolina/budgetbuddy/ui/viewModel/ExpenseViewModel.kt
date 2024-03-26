package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.ExpenseRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
):ViewModel() {
    val expenses: LiveData<List<Expense>> = expenseRepository.getAll()

    fun addExpenseAssignment(title: String, value: Float, date: String, assignmentId: Int, categoryId: Int) {
        val newExpense = Expense(title, value, date, assignmentId, categoryId)

        viewModelScope.launch {
            expenseRepository.insert(newExpense)
        }
    }

    fun insertAsList(expenses: List<Expense>) {
        expenseRepository.insertAsList(expenses)
    }

    fun deleteExpense(expense: Expense) {
        expenseRepository.delete(expense)
    }

    fun editExpense(expense: Expense) {
        expenseRepository.update(expense)
    }

    fun getExpensesByAssignmentIdSorted(assignmentId: Int): LiveData<List<Expense>> {
        return expenseRepository.getExpenseByAssignmentIdSorted(assignmentId)
    }

    fun getSumOfExpensesByAssignmentID(assignmentId: Int): LiveData<Float> {
        return expenseRepository.getSumByAssignmentId(assignmentId).map { sum ->
            sum ?: 0f
        }
    }

    fun isValidTitle(paymentTitle: String): Boolean {
        return paymentTitle.matches(Regex("^[a-zA-Z]{2,16}\$"))
    }

    fun isValidValue(paymentValue: String): Boolean {
        return paymentValue.matches(Regex("^(?!-)\\+?(\\d{1,3}(\\.\\d{3})*|(\\d+))(,\\d{2})?\$"))
    }

    fun isValidAssignmentValue(paymentValue: String): Boolean {
        return paymentValue.matches(Regex("^(?!-)\\+?(\\d{1,3}(\\.\\d{3})*|(\\d+))(,\\d{2})?\$"))
    }

    fun isValidDueDate(paymentDate: String): Boolean {
        return paymentDate.matches(Regex("^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.((19|20)\\d\\d)\$"))
    }

    fun convertGermanCurrencyStringToFloat(currencyString: String): Float {
        if (currencyString.isEmpty()) {
            return 0f
        }

        val normalizedString = currencyString
            .replace(".", "")
            .replace(",", ".")

        return normalizedString.toFloatOrNull() ?: 0f
    }


    companion object {
        fun floatToGermanCurrencyString(it: Float):String {
            return String.format(Locale.GERMAN, "%.2f", it)
        }
    }

    fun assignExpenseToSavingsGoal(value: Float, sgId: Int) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        // Expense from SavingDepot
        val negativeExpense = Expense(
            eName = "Zuweisung",
            eAmount = value*(-1),
            eDate = currentDate,
            eAssignment = 1,
            kId=1
        )
        expenseRepository.insert(negativeExpense)

        // Expense into SavingGoal
        val expense = Expense(
            eName = "Zuweisung",
            eAmount = value,
            eDate = currentDate,
            eAssignment = sgId,
            kId=1
        )
        expenseRepository.insert(expense)
    }

    fun isSavingGoalFulfilled(savingGoalId: Int, savingGoalAmount: Float): LiveData<Boolean> {
        val totalExpensesForGoal = getSumOfExpensesByAssignmentID(savingGoalId)

        return totalExpensesForGoal.map { totalExpenses ->
            totalExpenses >= savingGoalAmount
        }
    }
}