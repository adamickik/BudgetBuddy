package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.expense

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.category.Category
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.savingGoal.SavingGoal


@Entity(tableName = "expenses",
    foreignKeys = [
        ForeignKey(entity = SavingGoal::class,
            parentColumns = arrayOf("sgId"),
            childColumns = arrayOf("eAssignment"),
            onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Category::class,
            parentColumns = arrayOf("kId"),
            childColumns = arrayOf("kId"),
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
    val eAssignment: Int,
    @ColumnInfo(name="kId")
    var kId: Int? = null
)
{
    @PrimaryKey(autoGenerate = true)
    var eId: Int? = null
}
