package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.savingGoal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SavingGoalDao {
    @Insert
    fun insert(vararg savingGoal: SavingGoal)

    @Insert
    fun insert(savingGoal: SavingGoal): Long

    @Insert
    fun insertAsList(savingGoalList: List<SavingGoal>)

    @Query("SELECT * FROM savingGoals ORDER BY sgDueDate DESC")
    fun getAll(): LiveData<List<SavingGoal>>

    @Query("SELECT * FROM savingGoals WHERE sgId != 1")
    fun getAllOffline(): List<SavingGoal>

    @Query("SELECT * FROM savingGoals WHERE sgId LIKE :savingGoalId LIMIT 1")
    fun getById(savingGoalId: Int): LiveData<SavingGoal>

    @Query("SELECT * FROM savingGoals WHERE sgId LIKE :savingGoalId LIMIT 1")
    fun getByIdOffline(savingGoalId: Int): SavingGoal

    @Query("SELECT * FROM savingGoals WHERE sgName LIKE :savingGoalName LIMIT 1")
    fun getByName(savingGoalName: String): LiveData<SavingGoal>

    @Query("SELECT COUNT(*) FROM savingGoals")
    fun getCount(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM savingGoals WHERE sgId LIKE :savingGoalId")
    fun getCountById(savingGoalId: Int): Int

    @Update
    fun update(savingGoal: SavingGoal)

    @Delete
    fun delete(savingGoal: SavingGoal)
}