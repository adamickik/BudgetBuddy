package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model

import androidx.room.Database
import androidx.room.RoomDatabase
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.category.Category
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.category.CategoryDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.expense.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.expense.ExpenseDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.savingGoal.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.savingGoal.SavingGoalDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.tip.Tip
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.tip.TipDao


@Database(entities = [Expense::class, Tip::class, SavingGoal::class, Category::class ], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getExpenseDao() : ExpenseDao
    abstract fun getTipDao() : TipDao
    abstract fun getSavingGoalDao() : SavingGoalDao

    abstract fun getCategoryDao(): CategoryDao
}

