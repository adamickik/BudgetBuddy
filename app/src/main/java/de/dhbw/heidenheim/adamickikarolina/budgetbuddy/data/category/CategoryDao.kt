package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.category

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao {
    @Insert
    fun insert(category: Category)

    @Query("SELECT * FROM categories")
    fun getAllCategories(): LiveData<List<Category>>

    @Query("SELECT c.name AS categoryName, SUM(e.eAmount) AS totalAmount FROM expenses e JOIN categories c ON e.kId = c.kId GROUP BY e.kId")
    fun getCategoryExpensesSummary(): LiveData<List<CategoryExpenseSummary>>

    @Query("SELECT SUM(eAmount) FROM expenses WHERE kId = :kId")
    fun getSumByCategoryId(kId: Int): LiveData<Float>
}
