package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data

import androidx.room.Database
import androidx.room.RoomDatabase
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.category.Category
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.category.CategoryDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.ExpenseDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoalDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tip.Tip
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tip.TipDao


@Database(entities = [Expense::class, Tip::class, SavingGoal::class, Category::class ], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getExpenseDao() : ExpenseDao
    abstract fun getTipDao() : TipDao
    abstract fun getSavingGoalDao() : SavingGoalDao

    abstract fun getCategoryDao(): CategoryDao
}

