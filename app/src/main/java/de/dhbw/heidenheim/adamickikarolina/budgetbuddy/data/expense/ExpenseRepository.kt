package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.category.CategoryExpenseSummary
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
        return expenseDao.getSumByAssigmentId(assignmentId).map { sum ->
            sum ?: 0f
        }
    }

    fun getAllAssignmentIds(): LiveData<List<Int>> = expenseDao.getAllAssignmentIds()


    /*fun getSumByCategory(cId: Int): LiveData<Float>{
        return expenseDao.getSumByCategory(cId).map { sum ->
            sum ?: 0f
        }
    }*/

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
    fun getCategoryExpensesSummary(): LiveData<List<CategoryExpenseSummary>> {
        return expenseDao.getCategoryExpensesSummary()
    }
}