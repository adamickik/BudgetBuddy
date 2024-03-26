package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExpenseDao {
    @Insert
    fun insert(vararg expense: Expense)

    @Insert
    fun insertAsList(expenseList: List<Expense>)

    @Query("SELECT * FROM expenses")
    fun getAll(): LiveData<List<Expense>>

    @Query("SELECT DISTINCT eAssignment FROM expenses")
    fun getAllAssignmentIds(): LiveData<List<Int>>

    @Query("SELECT DISTINCT kId as categoryId, -SUM(eAmount) as sum FROM expenses WHERE eAmount<0 GROUP BY kId")
    fun getAllCategoryExpenses(): LiveData<List<CategorySum>>

    @Query("SELECT * FROM expenses WHERE eid LIKE :expenseId LIMIT 1")
    fun getById(expenseId: Int): LiveData<Expense>

    @Query("SELECT * FROM expenses WHERE eName LIKE :expenseName LIMIT 1")
    fun getByName(expenseName: String): LiveData<Expense>

    @Query("SELECT * FROM expenses WHERE eAssignment = :assignmentId")
    fun getByAssignmentId(assignmentId: Int): LiveData<List<Expense>>

    @Query("SELECT * FROM expenses WHERE eAssignment = :assignmentId ORDER BY eDate DESC")
    fun getByAssignmentIdSorted(assignmentId: Int): LiveData<List<Expense>>

    @Query("SELECT SUM(eAmount) FROM expenses WHERE eAssignment = :assignmentId")
    fun getSumByAssignmentId(assignmentId: Int): LiveData<Float>

    @Query("SELECT eAmount FROM expenses WHERE eAssignment = :assignmentId")
    fun getAmountsByAssignmentId(assignmentId: Int): LiveData<List<Float>>

    @Query("SELECT eAmount FROM expenses WHERE eAssignment = :categoryId")
    fun getAmountsByCategoryId(categoryId: Int): LiveData<List<Float>>

    @Query("SELECT sum(eAmount) FROM expenses WHERE eAssignment = :assignmentId")
    fun getSumByAssigmentIdOffline(assignmentId: Int): Float

    @Query("SELECT COALESCE(SUM(eAmount), 0) FROM expenses WHERE eAssignment = :assignmentId")
    fun getSumByAssigmentId(assignmentId: Int): LiveData<Float>

    @Query("SELECT SUM(eAmount) FROM expenses WHERE kId = :kId")
    fun getSumByCategoryId(kId: Int): LiveData<Float>

    @Query("SELECT -SUM(eAmount) FROM expenses WHERE kId != 1 and eAmount<0")
    fun getSumNegative(): LiveData<Float>

    @Update
    fun update(expense: Expense)

    @Delete
    fun delete(expense: Expense)
}