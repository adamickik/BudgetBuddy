package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.category

import androidx.lifecycle.LiveData
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    fun getAllCategories(): LiveData<List<Category>> {
        return categoryDao.getAllCategories()
    }

    fun insert(expense: Category) {
        categoryDao.insert(expense)
    }

    fun getNameByCategoryId(kId: Int): LiveData<String> {
        return categoryDao.getCategoryNameById(kId)
    }

    fun insertAsList(eList: List<Category>) {
        categoryDao.insertAsList(eList)
    }

    fun update(category: Category) {
        categoryDao.update(category)
    }

    fun delete(expense: Category) {
        categoryDao.delete(expense)
    }

}