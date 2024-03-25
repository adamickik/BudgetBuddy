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

    @Update
    fun update(category: Category)

    @Delete
    fun delete(category: Category)

    @Query("SELECT * FROM categories")
    fun getAllCategories(): LiveData<List<Category>>

    @Query("SELECT kName FROM categories WHERE kId = :kId")
    fun getCategoryNameById(kId: Int): LiveData<String>

    /*@Query("SELECT c.name AS categoryName, SUM(e.eAmount) AS totalAmount FROM expenses e JOIN categories c ON e.kId = c.kId GROUP BY e.kId")
    fun getCategoryExpensesSummary(): LiveData<List<CategoryExpenseSummary>>*/

    @Query("SELECT SUM(eAmount) FROM expenses WHERE kId = :kId")
    fun getSumByCategoryId(kId: Int): LiveData<Float>
}
