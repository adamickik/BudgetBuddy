package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal

import androidx.lifecycle.LiveData
import javax.inject.Inject

class SavingGoalRepository @Inject constructor(
    private val savingGoalDao: SavingGoalDao
) {
    fun getAllSavingGoals(): LiveData<List<SavingGoal>> {
        return savingGoalDao.getAll()
    }

    fun getSavingGoalById(sgId: Int): LiveData<SavingGoal> {
        return savingGoalDao.getById(sgId)
    }

    fun getSavingGoalCount(): Int {
        return savingGoalDao.getCount()
    }

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

    fun update(savingGoal: SavingGoal){
        savingGoalDao.update(savingGoal)
    }

    fun delete(savingGoal: SavingGoal) {
        savingGoalDao.delete(savingGoal)
    }
}