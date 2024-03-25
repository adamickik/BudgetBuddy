package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO wenn ein SG gelöscht wird und ein neues angelegt, muss dieses unbedingt mit der gleichen ID angelegt werden!
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
