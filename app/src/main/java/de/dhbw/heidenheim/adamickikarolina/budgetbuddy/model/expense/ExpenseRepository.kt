package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao
) {
    fun insert(expense: Expense) {
        expenseDao.insert(expense)
    }

    fun insertAsList(eList: List<Expense>) {
        expenseDao.insertAsList(eList)
    }

    fun getAll(): LiveData<List<Expense>> {
        return expenseDao.getAll()
    }

    fun getAllCategoryExpenses(): LiveData<List<CategorySum>>{
        return expenseDao.getAllCategoryExpenses()
    }

    fun getExpenseByAssignmentIdSorted(assignmentId: Int): LiveData<List<Expense>>{
        return expenseDao.getByAssignmentIdSorted(assignmentId)
    }

    fun getSumByAssignmentId(assignmentId: Int): LiveData<Float>{
        return expenseDao.getSumByAssigmentId(assignmentId).map { sum ->
            sum ?: 0f
        }
    }

    fun getSumNegative(): LiveData<Float>{
        return expenseDao.getSumNegative()
    }

    fun getAmountsByAssignmentId(assignmentId: Int): LiveData<List<Float>> {
        return expenseDao.getAmountsByAssignmentId(assignmentId)
    }

    fun update(expense: Expense) {
        expenseDao.update(expense)
    }

    fun delete(expense: Expense) {
        expenseDao.delete(expense)
    }
}