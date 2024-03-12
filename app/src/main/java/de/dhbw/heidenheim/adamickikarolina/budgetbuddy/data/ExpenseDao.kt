package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses")
    fun getAll(): LiveData<List<Expense>>

    @Query("SELECT * FROM expenses WHERE eid LIKE :expenseId LIMIT 1")
    fun getById(expenseId: Int): LiveData<Expense>

    @Query("SELECT * FROM expenses WHERE eName LIKE :expenseName LIMIT 1")
    fun getByName(expenseName: String): LiveData<Expense>

    @Insert
    fun insert(vararg expense: Expense)

    @Delete
    fun delete(expense: Expense)
}