package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tip

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="tipps")
data class Tip(
    @ColumnInfo(name="tTipp")
    val tTipp: String,
){
    @PrimaryKey(autoGenerate = true)
    var tId: Int? = null
}
