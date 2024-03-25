package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal

@Entity(tableName = "expenses",
    foreignKeys = [
        ForeignKey(entity = SavingGoal::class,
            parentColumns = arrayOf("sgId"),
            childColumns = arrayOf("eAssignment"),
            onDelete = ForeignKey.CASCADE)
    ])
data class Expense(
    @ColumnInfo(name="eName")
    var eName: String,
    @ColumnInfo(name="eAmount")
    var eAmount: Float,
    @ColumnInfo(name="eDate")
    var eDate: String,
    @ColumnInfo(name="eAssignment")
    val eAssignment: Int
)
{
    @PrimaryKey(autoGenerate = true)
    var eId: Int? = null
}
