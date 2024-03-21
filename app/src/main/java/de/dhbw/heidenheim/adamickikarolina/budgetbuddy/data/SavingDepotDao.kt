package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface SavingDepotDao {

    @Query("SELECT * FROM savingDepot LIMIT 1")
    fun getAll(): LiveData<SavingDepot>

    @Query("SELECT * FROM savingDepot WHERE sdId LIKE :savingDepotId LIMIT 1")
    fun getById(savingDepotId: Int): LiveData<SavingDepot>

    @Insert
    fun insert(savingDepot: SavingDepot)


    @Insert
    fun insertAsList(savingDepotList: List<SavingDepot>)

    @Update
    fun update(savingDepot: SavingDepot)

    @Delete
    fun delete(savingDepot: SavingDepot)
}