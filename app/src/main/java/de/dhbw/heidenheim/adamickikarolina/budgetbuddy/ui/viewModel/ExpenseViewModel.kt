package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.ExpenseRepository
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
):ViewModel() {
    val expenses: LiveData<List<Expense>> = expenseRepository.getAllExpenses()

    fun getAllExpenses() :LiveData<List<Expense>>{
        return expenseRepository.getAllExpenses()
    }

    fun addExpense(title: String, value: String, date: String) {
        val expenseValue = value.toFloatOrNull() ?: return
        val newExpense = Expense(title, expenseValue, date, 0)

        viewModelScope.launch {
            expenseRepository.insert(newExpense)
        }
    }

    fun addExpenseAssignment(title: String, value: String, date: String, assignmentId: Int, categoryId: Int) {
        val expenseValue = convertGermanCurrencyStringToFloat(value)
        val newExpense = Expense(title, expenseValue, date, assignmentId, categoryId)

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

    /*
    fun isValidValue(paymentValue: String): Boolean {
        return paymentValue.matches(Regex("^(?!-)\\+?(\\d{1,3}(\\.\\d{3})*|(\\d+))(,\\d{2})?\$"))
    }*/

    fun isValidValue(paymentValue: String): Boolean {
        return paymentValue.matches(Regex("^-?\\+?(\\d{1,3}(\\.\\d{3})*|(\\d+))(,\\d{2})?$"))
    }

    fun isValidAssignmentValue(paymentValue: String): Boolean {
        return paymentValue.matches(Regex("^(?!-)\\+?(\\d{1,3}(\\.\\d{3})*|(\\d+))(,\\d{2})?\$"))
    }

    fun isValidDueDate(paymentDate: String): Boolean {
        return paymentDate.matches(Regex("^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.((19|20)\\d\\d)\$"))
    }

    fun convertGermanCurrencyStringToFloat(currencyString: String): Float {
        val normalizedString = currencyString
            .replace(".", "")
            .replace(",", ".")
        return normalizedString.toFloat()
    }

    companion object {
        fun floatToGermanCurrencyString(it: Float):String {
            return String.format(Locale.GERMAN, "%.2f", it)
        }
    }


    fun assignExpenseToSavingsGoal(value: Float, assignment: SavingGoal) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        //Expense mit Abzug aus SparDepot
        val negativeExpense = assignment.sgId?.let {
            Expense(
                eName = assignment.sgName,
                eAmount = value*(-1),
                eDate = currentDate,
                eAssignment = 1,
                kId=1
            )
        }
        if (negativeExpense != null) {
            expenseRepository.insert(negativeExpense)
        }

        //Expense mit Zuweisung auf neues Sparziel
        val expense = assignment.sgId?.let {
            Expense(
                eName = assignment.sgName,
                eAmount = value,
                eDate = currentDate,
                eAssignment = it,
                kId=1
            )
        }
        if (expense != null) {
            expenseRepository.insert(expense)
        }
    }
}