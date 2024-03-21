package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="tipps")
data class Tipp(
    @ColumnInfo(name="tTipp")
    val tTipp: String,
){
    @PrimaryKey(autoGenerate = true)
    var tId: Int? = null
}
