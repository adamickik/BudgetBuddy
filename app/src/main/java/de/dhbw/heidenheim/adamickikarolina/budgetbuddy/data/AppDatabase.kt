package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data

import androidx.room.Database
import androidx.room.RoomDatabase
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.category.Category
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.ExpenseDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoalDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tipp.Tipp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tipp.TippDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.category.CategoryDao




@Database(entities = [Expense::class, Tipp::class, SavingGoal::class, Category::class ], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getExpenseDao() : ExpenseDao
    abstract fun getTippDao() : TippDao
    abstract fun getSavingGoalDao() : SavingGoalDao

    abstract fun getCategoryDao(): CategoryDao
}

