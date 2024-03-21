package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="savingGoals")
data class SavingGoal(
    @ColumnInfo(name="sgName")
    val sgName: String,
    @ColumnInfo(name="sgGoalAmount")
    val sgGoalAmount: Float,
    @ColumnInfo(name="sgDueDate")
    val sgDueDate: String,
){
    @PrimaryKey(autoGenerate = true)
    var sgId: Int? = null
}
