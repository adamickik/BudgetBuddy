package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.category

import androidx.lifecycle.LiveData
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    fun insert(expense: Category) {
        categoryDao.insert(expense)
    }

    fun insertAsList(eList: List<Category>) {
        categoryDao.insertAsList(eList)
    }

    fun getAll(): LiveData<List<Category>> {
        return categoryDao.getAll()
    }

    fun getNameByCategoryId(kId: Int): LiveData<String> {
        return categoryDao.getNameById(kId)
    }

    fun update(category: Category) {
        categoryDao.update(category)
    }

    fun delete(expense: Category) {
        categoryDao.delete(expense)
    }
}