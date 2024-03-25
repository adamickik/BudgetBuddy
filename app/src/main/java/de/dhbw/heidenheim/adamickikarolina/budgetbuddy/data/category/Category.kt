package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    var kId: Int? = null,
    @ColumnInfo(name="name")
    var name: String
)
data class CategoryExpenseSummary(
    val categoryName: String,
    val totalAmount: Float
)

