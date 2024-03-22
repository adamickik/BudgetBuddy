package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tipp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tipp.Tipp

@Dao
interface TippDao {
    @Query("SELECT * FROM tipps")
    fun getAll(): LiveData<List<Tipp>>

    @Query("SELECT * FROM tipps WHERE tId LIKE :tippId LIMIT 1")
    fun getById(tippId: Int): LiveData<Tipp>

    @Query("SELECT * FROM tipps WHERE tTipp LIKE :tippName LIMIT 1")
    fun getByName(tippName: String): LiveData<Tipp>

    @Insert
    fun insert(vararg tipp: Tipp)

    @Insert
    fun insertAsList(tippList: List<Tipp>)

    @Delete
    fun delete(tipp: Tipp)

    @Query("SELECT * FROM tipps ORDER BY RANDOM() LIMIT 1")
    fun getRandomTipp(): Tipp?

    @Query("SELECT COUNT(*) FROM tipps")
    fun getCount(): Int

}