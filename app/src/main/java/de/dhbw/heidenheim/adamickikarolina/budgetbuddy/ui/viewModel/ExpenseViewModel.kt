package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.ExpenseDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.ExpenseRepository
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingDepot.SavingDepot
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

    fun insertAsList(expenses: List<Expense>) {
        expenseRepository.insertAsList(expenses)
    }

    fun getExpensesByAssignmentId(assignmentId: Int): LiveData<List<Expense>> {
        return expenseRepository.getExpenseByAssignmentId(assignmentId)
    }
    fun getSumOfExpensesByAssigmentID(assignmentId: Int): LiveData<Float> {
        return expenseRepository.getSumByAssignmentId(assignmentId)
    }
    fun assignExpenseToSavingsGoal(value: Float, assigment: SavingGoal) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        //Expense mit Abzug aus SparDepot
        val negativeExpense = assigment.sgId?.let {
            Expense(
                eName = assigment.sgName,
                eAmount = value*(-1),
                eDate = currentDate,
                eAssignment = 0
            )
        }
        if (negativeExpense != null) {
            expenseRepository.insert(negativeExpense)
        }

        //Expense mit Zuweisung auf neues Sparziel
        val expense = assigment.sgId?.let {
            Expense(
                eName = assigment.sgName,
                eAmount = value,
                eDate = currentDate,
                eAssignment = it
            )
        }
        if (expense != null) {
            expenseRepository.insert(expense)
        }
    }
}