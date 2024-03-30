package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.savingGoal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="savingGoals")
data class SavingGoal(
    @ColumnInfo(name="sgName")
    var sgName: String,
    @ColumnInfo(name="sgGoalAmount")
    var sgGoalAmount: Float,
    @ColumnInfo(name="sgDueDate")
    var sgDueDate: String,
){
    @PrimaryKey(autoGenerate = true)
    var sgId: Int? = null
}
