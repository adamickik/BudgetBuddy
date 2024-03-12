package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @ColumnInfo(name="eName")
    val eName: String,
    @ColumnInfo(name="eAmount")
    val eAmount: Float,
    @ColumnInfo(name="eDate")
    val eDate: String,
    @ColumnInfo(name="eAssignment")
    val eAssignment: Int
)
{
    @PrimaryKey(autoGenerate = true)
    var eId: Int? = null
}
