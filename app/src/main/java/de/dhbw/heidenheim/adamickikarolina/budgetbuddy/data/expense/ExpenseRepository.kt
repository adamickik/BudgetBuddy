package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense

import androidx.lifecycle.LiveData
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao
) {
    fun getAllExpenses(): LiveData<List<Expense>> {
        return expenseDao.getAll()
    }

    fun getExpenseById(eId: Int): LiveData<Expense> {
        return expenseDao.getById(eId)
    }

    fun getExpenseByName(eName: String): LiveData<Expense> {
        return expenseDao.getByName(eName)
    }

    fun getExpenseByAssignmentId(assignmentId: Int): LiveData<List<Expense>>{
        return expenseDao.getByAssignmentId(assignmentId)
    }

    fun getExpenseByAssignmentIdSorted(assignmentId: Int): LiveData<List<Expense>>{
        return expenseDao.getByAssignmentIdSorted(assignmentId)
    }

    fun getSumByAssignmentIdOffline(assignmentId: Int): Float{
        return expenseDao.getSumByAssigmentIdOffline(assignmentId)
    }

    fun getSumByAssignmentId(assignmentId: Int): LiveData<Float>{
        return expenseDao.getSumByAssigmentId(assignmentId)
    }
    fun insert(expense: Expense) {
        expenseDao.insert(expense)
    }

    fun update(expense: Expense) {
        expenseDao.update(expense)
    }

    fun insertAsList(eList: List<Expense>) {
        expenseDao.insertAsList(eList)
    }

    fun delete(expense: Expense) {
        expenseDao.delete(expense)
    }
}