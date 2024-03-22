package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal

import androidx.lifecycle.LiveData
import javax.inject.Inject

class SavingGoalRepository @Inject constructor(
    private val savingGoalDao: SavingGoalDao
) {
    fun getAllSavingGoals(): LiveData<List<SavingGoal>> {
        return savingGoalDao.getAll()
    }

    fun getAllOfflineSavingGoals(): List<SavingGoal> {
        return savingGoalDao.getAllOffline()
    }

    fun getSavingGoalById(sgId: Int): LiveData<SavingGoal> {
        return savingGoalDao.getById(sgId)
    }

    fun getSavingGoalByIdOffline(sgId: Int): SavingGoal {
        return savingGoalDao.getByIdOffline(sgId)
    }

    fun getSavingGoalByName(sgName: String): LiveData<SavingGoal> {
        return savingGoalDao.getByName(sgName)
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

    fun delete(savingGoal: SavingGoal) {
        savingGoalDao.delete(savingGoal)
    }
}