package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.tip

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TipDao {
    @Insert
    fun insert(vararg tip: Tip)

    @Insert
    fun insertAsList(tipList: List<Tip>)

    @Query("SELECT * FROM tipps")
    fun getAll(): LiveData<List<Tip>>

    @Query("SELECT * FROM tipps WHERE tId LIKE :tippId LIMIT 1")
    fun getById(tippId: Int): LiveData<Tip>

    @Query("SELECT * FROM tipps WHERE tTipp LIKE :tippName LIMIT 1")
    fun getByName(tippName: String): LiveData<Tip>

    @Query("SELECT * FROM tipps ORDER BY RANDOM() LIMIT 1")
    fun getRandomTipp(): Tip?

    @Query("SELECT COUNT(*) FROM tipps")
    fun getCount(): Int

    @Delete
    fun delete(tip: Tip)
}