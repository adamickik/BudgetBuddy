package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses")
    fun getAll(): LiveData<List<Expense>>

    @Query("SELECT * FROM expenses WHERE eid LIKE :expenseId LIMIT 1")
    fun getById(expenseId: Int): LiveData<Expense>

    @Query("SELECT * FROM expenses WHERE eName LIKE :expenseName LIMIT 1")
    fun getByName(expenseName: String): LiveData<Expense>

    @Query("SELECT * FROM expenses WHERE eAssignment = :assignmentId")
    fun getByAssignmentId(assignmentId: Int): LiveData<List<Expense>>

    @Query("SELECT DISTINCT eAssignment FROM expenses")
    fun getAllAssignmentIds(): LiveData<List<Int>>

    @Query("SELECT SUM(eAmount) FROM expenses WHERE eAssignment = :assignmentId")
    fun getSumByAssignmentId(assignmentId: Int): LiveData<Float>

    @Query("SELECT * FROM expenses WHERE eAssignment = :assignmentId ORDER BY eDate DESC")
    fun getByAssignmentIdSorted(assignmentId: Int): LiveData<List<Expense>>

    @Query("SELECT eAmount FROM expenses WHERE eAssignment = :assignmentId")
    fun getAmountsByAssignmentId(assignmentId: Int): LiveData<List<Float>>

    @Query("SELECT sum(eAmount) FROM expenses WHERE eAssignment = :assignmentId")
    fun getSumByAssigmentIdOffline(assignmentId: Int): Float
/*
    @Query("SELECT sum(eAmount) FROM expenses WHERE eAssignment = :assignmentId")
    fun getSumByAssigmentId(assignmentId: Int): LiveData<Float>*/

    @Query("SELECT COALESCE(SUM(eAmount), 0) FROM expenses WHERE eAssignment = :assignmentId")
    fun getSumByAssigmentId(assignmentId: Int): LiveData<Float>

    // TODO Change to actual category
    @Query("SELECT COALESCE(SUM(eAmount), 0) FROM expenses WHERE eAssignment = :cId")
    fun getSumByCategory(cId: Int): LiveData<Float>

    @Insert
    fun insert(vararg expense: Expense)
    @Insert
    fun insertAsList(expenseList: List<Expense>)

    @Update
    fun update(expense: Expense)

    @Delete
    fun delete(expense: Expense)
}