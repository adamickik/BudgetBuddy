package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface SavingGoalDao {

    @Query("SELECT * FROM savingGoals")
    fun getAll(): LiveData<List<SavingGoal>>

    @Query("SELECT * FROM savingGoals")
    fun getAllOffline(): List<SavingGoal>

    @Query("SELECT * FROM savingGoals WHERE sgId LIKE :savingGoalId LIMIT 1")
    fun getById(savingGoalId: Int): LiveData<SavingGoal>

    @Query("SELECT * FROM savingGoals WHERE sgName LIKE :savingGoalName LIMIT 1")
    fun getByName(savingGoalName: String): LiveData<SavingGoal>

    @Insert
    fun insert(vararg savingGoal: SavingGoal)

    @Insert
    fun insertAsList(savingGoalList: List<SavingGoal>)

    @Delete
    fun delete(savingGoal: SavingGoal)
}