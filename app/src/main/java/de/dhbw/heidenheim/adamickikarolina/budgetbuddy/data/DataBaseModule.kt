package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.ExpenseDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoalDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tipp.TippDao
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
    fun provideTippsDao(appDatabase: AppDatabase): TippDao {
        return appDatabase.getTippDao()
    }

    @Provides
    fun provideSavingGoalDao(appDatabase: AppDatabase): SavingGoalDao {
        return appDatabase.getSavingGoalDao()
    }

    @Provides
    fun provideExpenseDao(appDatabase: AppDatabase): ExpenseDao {
        return appDatabase.getExpenseDao()
    }

}