package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @ColumnInfo(name="kName")
    var kName: String
)
{
    @PrimaryKey(autoGenerate = true)
    var kId: Int? = null
}
