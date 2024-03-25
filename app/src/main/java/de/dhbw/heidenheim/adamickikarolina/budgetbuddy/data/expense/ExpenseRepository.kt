package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao
) {
    fun getAllExpenses(): LiveData<List<Expense>> {
        return expenseDao.getAll()
    }

    fun getExpenseByAssignmentId(assignmentId: Int): LiveData<List<Expense>>{
        return expenseDao.getByAssignmentId(assignmentId)
    }

    fun getExpenseByAssignmentIdSorted(assignmentId: Int): LiveData<List<Expense>>{
        return expenseDao.getByAssignmentIdSorted(assignmentId)
    }

    fun getSumByAssignmentId(assignmentId: Int): LiveData<Float>{
        return expenseDao.getSumByAssigmentId(assignmentId).map { sum ->
            sum ?: 0f
        }
    }

    fun getAllAssignmentIds(): LiveData<List<Int>> = expenseDao.getAllAssignmentIds()

    fun getSumNegative():  LiveData<Float> = expenseDao.getSumNegative()

    fun getAllCategoryExpenses(): LiveData<List<CategorySum>> = expenseDao.getAllCategoryExpenses()


    fun getSumByCategoryId(kId: Int): LiveData<Float> {
        return expenseDao.getSumByCategoryId(kId)
    }

    fun getAmountsByAssignmentId(assignmentId: Int): LiveData<List<Float>> {
        return expenseDao.getAmountsByAssignmentId(assignmentId)
    }

    fun insert(expense: Expense) {
        expenseDao.insert(expense)
    }

    fun insertAsList(eList: List<Expense>) {
        expenseDao.insertAsList(eList)
    }

    fun update(expense: Expense) {
        expenseDao.update(expense)
    }

    fun delete(expense: Expense) {
        expenseDao.delete(expense)
    }
}