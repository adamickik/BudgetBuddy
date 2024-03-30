package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.category.CategoryDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.expense.ExpenseDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.savingGoal.SavingGoalDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.tip.TipDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "budgetbuddyDB"
        ).allowMainThreadQueries().build()
    }
    @Provides
    fun provideTipsDao(appDatabase: AppDatabase): TipDao {
        return appDatabase.getTipDao()
    }

    @Provides
    fun provideSavingGoalDao(appDatabase: AppDatabase): SavingGoalDao {
        return appDatabase.getSavingGoalDao()
    }

    @Provides
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao {
        return appDatabase.getCategoryDao()
    }

    @Provides
    fun provideExpenseDao(appDatabase: AppDatabase): ExpenseDao {
        return appDatabase.getExpenseDao()
    }

}