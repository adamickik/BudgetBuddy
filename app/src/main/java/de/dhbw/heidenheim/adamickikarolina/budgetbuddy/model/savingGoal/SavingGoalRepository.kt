package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.savingGoal

import androidx.lifecycle.LiveData
import javax.inject.Inject

class SavingGoalRepository @Inject constructor(
    private val savingGoalDao: SavingGoalDao
) {
    fun insert(savingGoal: SavingGoal) {
        savingGoalDao.insert(savingGoal)
    }

    fun insertAsList(sgList: List<SavingGoal>) {
        savingGoalDao.insertAsList(sgList)
    }

    fun insertAsListAndGetIds(savingGoals: List<SavingGoal>): List<Long> {
        return savingGoals.map { savingGoal ->
            savingGoalDao.insert(savingGoal)
        }
    }

    fun getAllSavingGoals(): LiveData<List<SavingGoal>> {
        return savingGoalDao.getAll()
    }

    fun getSavingGoalById(sgId: Int): LiveData<SavingGoal> {
        return savingGoalDao.getById(sgId)
    }

    fun getSavingGoalCount(): LiveData<Int> {
        return savingGoalDao.getCount()
    }

    fun update(savingGoal: SavingGoal){
        savingGoalDao.update(savingGoal)
    }

    fun delete(savingGoal: SavingGoal) {
        savingGoalDao.delete(savingGoal)
    }
}