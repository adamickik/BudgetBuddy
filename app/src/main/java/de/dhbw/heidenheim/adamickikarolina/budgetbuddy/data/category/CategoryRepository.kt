package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.category.CategoryExpenseSummary
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    fun getAllCategories(): LiveData<List<Category>> {
        return categoryDao.getAll()
    }




    fun insert(expense: Expense) {
        expenseDao.insert(expense)
    }

    fun insertAsList(eList: List<Expense>) {
        expenseDao.insertAsList(eList)
    }

    fun update(expense: Expense) {
        expenseDao.update(expense)
    }

    fun delete(expense: Expense) {
        expenseDao.delete(expense)
    }

}