package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.category

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CategoryDao {
    @Insert
    fun insert(category: Category)

    @Insert
    fun insertAsList(categoryList: List<Category>)

    @Query("SELECT * FROM categories")
    fun getAll(): LiveData<List<Category>>

    @Query("SELECT kName FROM categories WHERE kId = :kId")
    fun getNameById(kId: Int): LiveData<String>

    @Update
    fun update(category: Category)

    @Delete
    fun delete(category: Category)
}
