package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingDepot

import androidx.lifecycle.LiveData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal
import javax.inject.Inject

class SavingDepotRepository @Inject constructor(
    private val savingDepotDao: SavingDepotDao
)  {
    fun getSavingDepot(): LiveData<SavingDepot> {
        return savingDepotDao.getAll()
    }

    fun getSavingDepotById(sdId: Int): LiveData<SavingDepot> {
        return savingDepotDao.getById(sdId)
    }

    fun insert(savingDepot: SavingDepot) {
        savingDepotDao.insert(savingDepot)
    }

    fun insertAsList(sdList: List<SavingDepot>) {
        savingDepotDao.insertAsList(sdList)
    }

    fun delete(savingDepot: SavingDepot) {
        savingDepotDao.delete(savingDepot)
    }

    fun update(savingDepot: SavingDepot) {
        savingDepotDao.update(savingDepot)
    }
}