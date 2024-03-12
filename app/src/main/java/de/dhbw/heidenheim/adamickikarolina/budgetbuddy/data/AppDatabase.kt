package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Expense::class, Tipp::class, SavingGoal::class, SavingDepot::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getExpenseDao() : ExpenseDao
    abstract fun getTippDao() : TippDao
    abstract fun getSavingGoalDao() : SavingGoalDao
    abstract fun getSavingDepotDao() : SavingDepotDao
}